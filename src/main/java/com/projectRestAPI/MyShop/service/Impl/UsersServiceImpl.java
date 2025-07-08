package com.projectRestAPI.MyShop.service.Impl;


import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.ChangePasswordRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.dto.response.UserStatisticsResponse;
import com.projectRestAPI.MyShop.enums.LoginType;
import com.projectRestAPI.MyShop.enums.Role;
import com.projectRestAPI.MyShop.mapper.User.UserMapper;
import com.projectRestAPI.MyShop.model.OtpVerification;
import com.projectRestAPI.MyShop.model.Roles;
import com.projectRestAPI.MyShop.model.UserImage;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.OtpVerificationRepository;
import com.projectRestAPI.MyShop.repository.RolesRepository;
import com.projectRestAPI.MyShop.repository.UserImageRepository;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.EmailService;
import com.projectRestAPI.MyShop.service.UsersService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UsersServiceImpl extends BaseServiceImpl<Users,Long, UsersRepository> implements UsersService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private OtpVerificationRepository otpVerificationRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private EmailService emailService;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public ResponseEntity<?> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}",authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<Users> users=repository.findAll();
        List<UserResponse> userResponses = userMapper.toListUserResponse(users);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(userResponses)
                .build();
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        Page<Users> response = getAll(params,pageable,sort,null);
        List<Users> users = response.getContent();
        List<UserResponse> userResponses =userMapper.toListUserResponse(users);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("users",userResponses);
                    put("count",response.getTotalElements());
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @Override
    public boolean isUserExists(String UserName) {
        return repository.existsByUsername(UserName);
    }

    protected static final List<String> ROLE_NAMES = Arrays.asList("ADMIN", "MANAGER", "EMPLOYEE");

    public boolean userHasRoleInList(List<String> userRoles) {
        return userRoles.stream()
                .anyMatch(ROLE_NAMES::contains);
    }

    @Override
    public ResponseEntity<ResponseObject> addUser(UserRequest userRequest,MultipartFile file) {
        if(isUserExists(userRequest.getUsername())){
            return new ResponseEntity<>(new ResponseObject("Fail", "Trùng tên đăng nhập", 1, null), HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByEmail(userRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseObject("Fail", "Email đã được sử dụng.", 1, null), HttpStatus.BAD_REQUEST);
        }
        Users users = mapper.map(userRequest, Users.class);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if (userRequest.getTypeLogin() == 0 && userRequest.getPassword() != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        } else {
            users.setPassword(null);
        }
        Map<Integer, LoginType> loginTypeMap = Map.of(
                0, LoginType.NORMAL_LOGIN,
                1, LoginType.FACEBOOK_LOGIN,
                2, LoginType.GOOGLE_LOGIN,
                3, LoginType.GITHUB_LOGIN
        );

        LoginType selectedLoginType = loginTypeMap.get(userRequest.getTypeLogin());
        users.setTypeLogin(selectedLoginType != null ? selectedLoginType.getLoginType() : null);
        List<Roles> roles = new ArrayList<>();
        roles.add(rolesRepository.findByName(Role.USER.getRoles()));
        users.setRoles(roles);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isCreatedByAdmin = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
        users.setEnable(isCreatedByAdmin);
        Users users1 = repository.save(users);
        if (file != null && !file.isEmpty()) {
            UserImage userImage = saveImageFile(file, users1);
            users1.setUserImage(userImage);
        }
        if (!isCreatedByAdmin) {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            OtpVerification otpEntity = OtpVerification.builder()
                    .email(users1.getEmail())
                    .otp(otp)
                    .expirationTime(LocalDateTime.now().plusMinutes(2))
                    .build();
            otpVerificationRepository.save(otpEntity);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(users1.getEmail());
            message.setSubject("Mã OTP xác thực");
            message.setText("Mã OTP của bạn là: " + otp);
            mailSender.send(message);
        }

        return ResponseUtil.buildResponse(
                "200",
                isCreatedByAdmin
                        ? "Tạo tài khoản thành công (không cần OTP)."
                        : "Đăng ký thành công. Vui lòng kiểm tra email để xác thực OTP.",
                1,
                users1.getEmail(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateUser(UserRequest userRequest, MultipartFile file) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        Optional<Users> opt = repository.findById(userRequest.getId());
        if (opt.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Users existingUser = opt.get();

        // Nếu không phải admin thì phải đúng chủ tài khoản
        if (!isAdmin && !existingUser.getUsername().equals(currentUsername)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (file != null && !file.isEmpty()) {
            UserImage userImage = saveImageFile(file, existingUser);
            existingUser.setUserImage(userImage);
        }else {
            existingUser.setUserImage(existingUser.getUserImage());
        }
        if(userRequest.getFullName() != null){
            existingUser.setFullName(userRequest.getFullName()) ;
        }
        if(userRequest.getGender() != null){
            existingUser.setGender(userRequest.getGender());
        }
        if (userRequest.getPhone() != null){
            existingUser.setPhone(userRequest.getPhone());
        }
        if(userRequest.getBirthday() != null){
            existingUser.setBirthday(userRequest.getBirthday());
        }
        List<Roles> roles = new ArrayList<>();
        for (int i=0 ; i < userRequest.getRoles().size() ; i++){
            roles.add(rolesRepository.findById(userRequest.getRoles().get(i).getId()).orElseThrow(()->
                    new AppException(ErrorCode.BAD_REQUEST)
            ));
        }
        if(isAdmin){
            existingUser.setRoles(roles);
        }

        repository.save(existingUser);

        return ResponseUtil.buildResponse(
                "success",
                "Cập nhật thông tin thành công",
                1,
                null,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ResponseObject> requestChangePassword(ChangePasswordRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = repository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String purpose = "";
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            // Đổi mật khẩu
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
            }

            purpose = "CHANGE_PASSWORD";
        } else if (request.getNewEmail() != null && !request.getNewEmail().isBlank()) {
            boolean existsByEmail = repository.existsByEmail(request.getNewEmail());
            if(request.getNewEmail().equals(user.getEmail()) || existsByEmail)  {
                throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            // Đổi email
            purpose = "CHANGE_EMAIL";
        } else if (request.getNewPhone() != null && !request.getNewPhone().isBlank()) {
            // Đổi số điện thoại
            purpose = "CHANGE_PHONE";
        } else {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        // Tạo và lưu OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        OtpVerification otpEntity = OtpVerification.builder()
                .email(Objects.equals(purpose, "CHANGE_EMAIL") ? request.getNewEmail() : user.getEmail() )
                .otp(otp)
                .expirationTime(LocalDateTime.now().plusMinutes(2))
                .build();

        otpVerificationRepository.findFirstByEmailOrderByExpirationTimeDesc(user.getEmail());
        otpVerificationRepository.save(otpEntity);

        // Gửi email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Objects.equals(purpose, "CHANGE_EMAIL") ? request.getNewEmail() : user.getEmail());
        message.setSubject("Xác nhận " + convertPurposeToMessage(purpose));
        message.setText("Mã OTP xác nhận của bạn là: " + otp);
        mailSender.send(message);

        return ResponseUtil.buildResponse("200", "OTP đã được gửi đến email.", 1, null, HttpStatus.OK);
    }

    // Hàm phụ trợ để mô tả mục đích thân thiện với người dùng
    private String convertPurposeToMessage(String purpose) {
        return switch (purpose) {
            case "CHANGE_PASSWORD" -> "đổi mật khẩu";
            case "CHANGE_EMAIL" -> "đổi email";
            case "CHANGE_PHONE" -> "đổi số điện thoại";
            default -> "hành động";
        };
    }

    @Override
    public ResponseEntity<ResponseObject> confirmChangePassword(ChangePasswordRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = repository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Email dùng để xác thực OTP (có thể là email mới hoặc hiện tại)
        String targetEmail = request.getEmail() != null ? request.getEmail() : user.getEmail();

        // Kiểm tra OTP
        OtpVerification otpEntity = otpVerificationRepository
                .findFirstByEmailOrderByExpirationTimeDesc(request.getNewEmail() != null ? request.getNewEmail() : targetEmail)
                .orElseThrow(() -> new AppException(ErrorCode.OTP_NOT_FOUND));

        if (!otpEntity.getOtp().equals(request.getOtp())) {
            throw new AppException(ErrorCode.OTP_IS_INCORRECT);
        }

        if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_HAS_EXPIRED);
        }

        // Nếu có yêu cầu đổi mật khẩu
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
            }

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        // Cập nhật email nếu có yêu cầu và khác hiện tại
        if (request.getNewEmail() != null) {
            if (!request.getNewEmail().equals(user.getEmail())) {
                if (repository.existsByEmail(request.getNewEmail())) {
                    throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
                user.setEmail(request.getNewEmail());
            }
        }


        // Cập nhật số điện thoại nếu có yêu cầu và khác hiện tại
        if (request.getNewPhone() != null && !request.getNewPhone().equals(user.getPhone())) {
            user.setPhone(request.getNewPhone());
        }

        repository.save(user);
        otpVerificationRepository.delete(otpEntity);

        return ResponseUtil.buildResponse("200", "Cập nhật thành công.", 1, null, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseObject> forgotPassword(String email) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = repository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Xóa OTP cũ nếu có
        otpVerificationRepository.deleteByEmail(email);

        // Tạo OTP mới
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6 chữ số
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        OtpVerification otpEntity = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expirationTime(expirationTime)
                .build();

        otpVerificationRepository.save(otpEntity);

        emailService.sendOtpEmail(user.getEmail(),otp,"Xác nhận đổi mật khẩu");

        return ResponseUtil.buildResponse("200", "OTP đã được gửi đến email.", 1, null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> confirmForgotPassword(ChangePasswordRequest request) {
        // Tìm user
        Users user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy OTP gần nhất
        OtpVerification otpEntity = otpVerificationRepository
                .findFirstByEmailOrderByExpirationTimeDesc(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.OTP_NOT_FOUND));

        // Kiểm tra OTP đúng và chưa hết hạn
        if (!otpEntity.getOtp().equals(request.getOtp())) {
            throw new AppException(ErrorCode.OTP_IS_INCORRECT);
        }

        if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_HAS_EXPIRED);
        }

        // Kiểm tra xác nhận mật khẩu
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }

        // Cập nhật mật khẩu
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(request.getNewPassword()));
        repository.save(user);

        // Xóa OTP sau khi dùng
        otpVerificationRepository.delete(otpEntity);

        return ResponseUtil.buildResponse("200", "Mật khẩu đã được đặt lại thành công.", 1, null, HttpStatus.OK);
    }




    @Override
    public ResponseEntity<?> getMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> usersOpt = repository.findByUsername(name);
        if(usersOpt.isPresent()){
            Users user =usersOpt.get();
            return new ResponseEntity<>(new ResponseObject("200","Lấy dữ liệu thành công",1,userMapper.toUserResponse(user)) , HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @Override
    public Users getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return repository.findByUsername(name)
                .orElse(null);
    }

    @Override
    public ResponseEntity<ResponseObject> verifyOtp(String email, String otp, String purpose) {
        Optional<OtpVerification> otpOpt = otpVerificationRepository.findByEmailAndPurpose(email, purpose);
        if (otpOpt.isEmpty()) {
            throw new AppException(ErrorCode.OTP_NOT_FOUND);
        }

        OtpVerification otpEntity = otpOpt.get();
        if (!otpEntity.getOtp().equals(otp)) {
            throw new AppException(ErrorCode.OTP_IS_INCORRECT);
        }
        if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_HAS_EXPIRED);
        }

        // Tuỳ theo mục đích, thực hiện hành động phù hợp
        switch (purpose.toUpperCase()) {
            case "REGISTER" -> {
                Users user = repository.findByEmail(email).orElseThrow();
                user.setEnable(true);
                repository.save(user);
            }
            case "FORGOT_PASSWORD" -> {
                // Cho phép chuyển sang bước nhập mật khẩu mới
                // Có thể trả token tạm thời hoặc gán cờ vào cache/session
            }
            case "ORDER_CONFIRM" -> {
                // Đánh dấu đơn hàng đã được xác nhận bởi người dùng
            }
            case "CHANGE_PASSWORD" -> {
                // Cho phép đổi mật khẩu
            }
            default -> throw new AppException(ErrorCode.OTP_PURPOSE_INVALID);
        }

        // Xóa OTP sau khi dùng
        otpVerificationRepository.delete(otpEntity);

        return ResponseUtil.buildResponse(
                "200",
                "OTP hợp lệ. Thao tác " + purpose + " đã được xác nhận.",
                1,
                null,
                HttpStatus.OK
        );
    }


    @Override
    public ResponseEntity<ResponseObject> resendOtp(String email, String purpose) {
        Optional<Users> userOptional = repository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseUtil.buildResponse(
                    "404",
                    "Email không tồn tại trong hệ thống.",
                    0,
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        Users user = userOptional.get();

        // Trường hợp đăng ký -> đã kích hoạt thì không gửi lại
        if (purpose.equalsIgnoreCase("REGISTER") && user.getEnable()) {
            return ResponseUtil.buildResponse(
                    "400",
                    "Tài khoản đã được kích hoạt.",
                    0,
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }

        // Xoá mã OTP cũ theo email và purpose
        otpVerificationRepository
                .findByEmailAndPurpose(email, purpose)
                .ifPresent(otpVerificationRepository::delete);

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        OtpVerification newOtp = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .purpose(purpose)
                .expirationTime(LocalDateTime.now().plusMinutes(2))
                .build();
        otpVerificationRepository.save(newOtp);

        String subject = switch (purpose.toUpperCase()) {
            case "FORGOT_PASSWORD" -> "Mã OTP khôi phục mật khẩu";
            case "CHANGE_PASSWORD" -> "Mã OTP đổi mật khẩu";
            case "ORDER_CONFIRM" -> "Xác nhận đơn hàng bằng OTP";
            case "CHANGE_EMAIL" -> "Xác nhận thay đổi email";
            case "CHANGE_PHONE" -> "Xác nhận thay đổi số điện thoại";
            case "ORDER" -> "Xác nhận mua hàng";
            default -> "Mã OTP xác thực";
        };

        String text = "Mã OTP của bạn là: " + otp;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        return ResponseUtil.buildResponse(
                "200",
                "Mã OTP đã được gửi tới email. Vui lòng kiểm tra.",
                1,
                email,
                HttpStatus.OK
        );
    }


    public UserResponse validUser(Users users) {
        UserResponse userResponse = mapper.map(users, UserResponse.class);

        List<String> listRoleName = users.getRoles().stream()
                .map(Roles::getName)
                .toList();


        return userResponse;
    }

    private UserImage saveImageFile(MultipartFile file, Users users){
        if(file != null && !file.isEmpty()){
            String fileType = file.getContentType();
            if(isValidImage(fileType)){
                try {
                    String fileName = writeFile(file);
                    return UserImage.builder()
                            .name(fileName)
                            .file(file.getBytes())
                            .type(fileType)
                            .user(users)
                            .build();
                }catch (IOException e){
                    throw new AppException(ErrorCode.READ_WRITE_ERROR);
                }
            }
        }else {
            throw new RuntimeException("Invalid file type. Only image and video files are allowed.");
        }
        return null;
    }

    public boolean isValidImage(String fileType) {
        return fileType != null && fileType.startsWith("image/");
    }

    public String writeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    @Override
    public ResponseEntity<ResponseObject> getUserStatisticsByMonth() {
        UserStatisticsResponse result = new UserStatisticsResponse();
        result.setTotalPerMonth(repository.getUserCountByMonth());
        result.setGenderCount(repository.getGenderCounts());
        result.setRoleCounts(repository.getRoleCounts());
        return ResponseUtil.buildResponse("200","Lấy dữ liệu thành công",1,result,HttpStatus.OK);
    }
}
