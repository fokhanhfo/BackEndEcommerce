package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.ChangePasswordRequest;
import com.projectRestAPI.MyShop.dto.request.RolesRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.service.RolesService;
import com.projectRestAPI.MyShop.service.UsersService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;

    @Autowired
    private DiscountUserRepository discountUserRepository;

//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getAll(){
//        return usersService.getAll();
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "idDiscount", required = false) Long idDiscount,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "role", defaultValue = "") String role,
            @RequestParam(value = "typeLogin", defaultValue = "") String typeLogin,
            @RequestParam(value = "enable", required = false) Boolean enable) {

        Pageable pageable = PageRequest.of(page, limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();

        // Chỉ thêm criteria typeLogin nếu khác null và khác rỗng
        if (typeLogin != null && !typeLogin.trim().isEmpty()) {
            SearchCriteriaUtils.addCriteria(criteriaList, "typeLogin", ":", typeLogin);
        }

        // Chỉ thêm criteria enable nếu khác null
        if (enable != null) {
            SearchCriteriaUtils.addCriteria(criteriaList, "enable", ":", enable);
        }

        // Lọc user chưa có discount
        if (idDiscount != null) {
            List<Long> userIds = discountUserRepository.findUserIdsByDiscountId(idDiscount);

            if (!userIds.isEmpty()) {
                criteriaList.add(new SearchCriteria("id", "notIn", userIds));
            }
        }

        // Lọc theo role
        if (role != null && !role.trim().isEmpty()) {
            List<String> roles = Arrays.stream(role.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            if (!roles.isEmpty()) {
                criteriaList.add(new SearchCriteria("roles.name", "in", roles));
            }
        }

        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }

        return usersService.getAll(criteriaList, pageable, sortParams);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Users getId(@PathVariable Long id){
//        Optional<Users> opt = usersService.findById(id);
//        if(opt.isEmpty()){
//            return new ResponseEntity<>(new ResponseObject("Fail", "Không Thấy ID", 1, null), HttpStatus.BAD_REQUEST);
//        }
//        UserRequest userRequest =mapper.map(opt, UserRequest.class);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(userRequest.getUsername().equals(authentication.getName())){
//            return new ResponseEntity<>(userRequest, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new ResponseObject("0","Người dùng không hợp lệ",0,null), HttpStatus.BAD_REQUEST);
        return usersService.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @GetMapping("/myInfo")
    public ResponseEntity<?> getMyInFo(){
        return usersService.getMyInfo();
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(@RequestPart("userRequest") UserRequest userRequest,
                                 @RequestPart(value = "file", required = false) MultipartFile file){
        return usersService.addUser(userRequest,file);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestPart("userRequest") UserRequest userRequest,
                                    @RequestPart(value = "file", required = false) MultipartFile file){
        return usersService.updateUser(userRequest,file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return usersService.delete(id);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseObject> verifyOtp(@RequestParam String email, @RequestParam String otp,@RequestParam String purpose) {
        return usersService.verifyOtp(email,otp, purpose);
    }

    @PostMapping("/resendOtp")
    public ResponseEntity<ResponseObject> resendOtp(@RequestParam String email,@RequestParam String purpose) {
        return usersService.resendOtp(email,purpose);
    }

    @GetMapping("/getDataStatistics")
    public ResponseEntity<ResponseObject> getDataStatistics() {
        return usersService.getUserStatisticsByMonth();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordRequest request) {
        return usersService.requestChangePassword(request);
    }

    @PostMapping("/confirm-change-password")
    public ResponseEntity<ResponseObject> confirmChangePassword(@RequestBody ChangePasswordRequest request) {
        return usersService.confirmChangePassword(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return usersService.forgotPassword(email);
    }

    @PostMapping("/confirm-forgot-password")
    public ResponseEntity<ResponseObject> confirmForgotPassword(
            @RequestBody ChangePasswordRequest request
    ) {
        return usersService.confirmForgotPassword(request);
    }


}
