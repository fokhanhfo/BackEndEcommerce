package com.projectRestAPI.MyShop.service.Impl.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.projectRestAPI.MyShop.dto.request.*;
import com.projectRestAPI.MyShop.dto.response.IntrospectResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.LoginType;
import com.projectRestAPI.MyShop.model.InvalidatedToken;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.InvalidatedTokenRepository;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.AuthenticationService;
import com.projectRestAPI.MyShop.service.UsersService;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${app.jwt}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${app.jwt.accessTokenExpiration}")
    protected long accessTokenExpiration;

    @NonFinal
    @Value("${app.jwt.refreshTokenExpiration}")
    protected long refreshTokenExpiration;

    @Autowired
    private UsersService usersService;

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception {
        String token = introspectRequest.getToken();
        boolean isValid = true;
        try{
            verifyToken(token,false);
        }catch (Exception e){
            isValid=false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public ResponseEntity<ResponseObject> authenticate(AuthenticationRequest authenticationRequest) {
        log.info("SignKey:{}",SIGNER_KEY);
        Optional<Users> userOpt = usersRepository.findByUsername(authenticationRequest.getUsername());
        if(userOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("400", "Không tìm thấy tài khoản", 0, authenticationRequest), HttpStatus.BAD_REQUEST);
        }
        Users user = userOpt.get();
        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword());
        if(!authenticated){
            return new ResponseEntity<>(new ResponseObject("400", "Tài khoản hoặc mật khẩu sai", 0, authenticationRequest), HttpStatus.BAD_REQUEST);
        }
        if (!user.getEnable()) {
            return new ResponseEntity<>(
                    new ResponseObject("403", "Tài khoản chưa được kích hoạt", 0, user.getEmail()),
                    HttpStatus.FORBIDDEN
            );
        }
        String token =generateToken(user);

        return new ResponseEntity<>(new ResponseObject("200", "Đăng nhập thành công", 0, token), HttpStatus.OK);
    }

    public void logout(LogoutRequest request) throws Exception {
        SignedJWT signToken = verifyToken(request.getToken(),false);

        String jit =signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =InvalidatedToken.builder()
                .idCode(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    public ResponseEntity<ResponseObject> facebookLogin(Map<String,String> payload){
        String accessToken = payload.get("token");

        // Gửi Access Token đến Facebook Graph API để lấy thông tin người dùng
        String url = "https://graph.facebook.com/me?fields=id,name,email,birthday,picture&access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        FaceBookUserRequest faceBookUserRequest;
        try{
            faceBookUserRequest = objectMapper.readValue(response.getBody(), FaceBookUserRequest.class);

            Optional<Users> usersOptional = usersRepository.findByUsername(faceBookUserRequest.getId());
            if(usersOptional.isEmpty()){
                UserRequest userRequest = UserRequest.builder()
                        .fullName(faceBookUserRequest.getName())
                        .username(faceBookUserRequest.getId())
                        .typeLogin(LoginType.FACEBOOK_LOGIN.getLoginType())
                        .password("khanhkomonny")
                        .build();
                usersService.addUser(userRequest,null);
                return authenticate(new AuthenticationRequest(faceBookUserRequest.getId(), "khanhkomonny"));
            }
            Users users = usersOptional.get();
            return authenticate(new AuthenticationRequest(users.getUsername(), "khanhkomonny"));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseObject("400", "Đăng nhập không thành công", 0, null), HttpStatus.BAD_REQUEST);
        }
    }

    private SignedJWT verifyToken(String token ,boolean isRefresh) throws Exception {
        //tạo một đối tượng JWSVerifier để xác minh chữ ký của token. Trong trường hợp này, nó sử dụng MACVerifier,
        // một loại JWSVerifier dựa trên mã hóa MAC (Message Authentication Code), và cung
        // cấp khóa để xác thực (SIGNER_KEY).
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT =SignedJWT.parse(token);

        Date expityTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(refreshTokenExpiration,ChronoUnit.DAYS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified =  signedJWT.verify(verifier);

        if(!(verified && expityTime.after(new Date()))){
            throw new Exception("JWT token is either invalid or expired");
        }

        if(invalidatedTokenRepository.existsByIdCode(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new Exception("Đã Logout");
        }

        return signedJWT;
    }

    public ResponseEntity<?> refreshToken(RefreshRequest request) throws Exception {
        System.out.println("đã refresh");
        var signedJWT =verifyToken(request.getToken(),true);

//        khi refreshToken thì phải cho token ấy logout đã
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken =InvalidatedToken.builder()
                .idCode(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = usersRepository.findByUsername(username).orElseThrow();

        var token = generateToken(user);

        return new ResponseEntity<>(new ResponseObject("200", "Tài khoản đúng", 0, token), HttpStatus.OK);
    }

    private String generateToken(Users users){
        JWSHeader header =new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(accessTokenExpiration, ChronoUnit.DAYS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(users))
                .claim("fullName",users.getFullName())
                .claim("gender",users.getGender())
                .claim("email",users.getEmail())
                .claim("image", users.getUserImage() != null
                        ? "http://localhost:8080/userImage/" + users.getUserImage().getName()
                        : null)
                .build();
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject =new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users users){
        StringJoiner stringJoiner =new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(users.getRoles())){
            users.getRoles().forEach(roles -> {
                stringJoiner.add("ROLE_"+roles.getName());
                if(!CollectionUtils.isEmpty(roles.getPermissions())) {
                    roles.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return  stringJoiner.toString();

    }
}
