package com.projectRestAPI.studensystem.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.projectRestAPI.studensystem.dto.request.AuthenticationRequest;
import com.projectRestAPI.studensystem.dto.request.IntrospectRequest;
import com.projectRestAPI.studensystem.dto.request.LogoutRequest;
import com.projectRestAPI.studensystem.dto.request.RefreshRequest;
import com.projectRestAPI.studensystem.dto.response.AuthenticationResponse;
import com.projectRestAPI.studensystem.dto.response.IntrospectResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.InvalidatedToken;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.InvalidatedTokenRepository;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import com.projectRestAPI.studensystem.service.AuthenticationService;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${app.jwt}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception {
        String token = introspectRequest.getToken();
        boolean isValid = true;
        try{
            verifyToken(token);
        }catch (Exception e){
            isValid=false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public ResponseEntity<ResponseObject> authenticate(AuthenticationRequest authenticationRequest) {
        Optional<Users> userOpt = usersRepository.findByUsername(authenticationRequest.getUsername());
        if(userOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("false", "Không tìm thấy tài khoản", 0, authenticationRequest), HttpStatus.BAD_REQUEST);
        }
        Users user = userOpt.get();
        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword());
        if(!authenticated){
            return new ResponseEntity<>(new ResponseObject("false", "Tài khoản hoặc mật khẩu sai", 0, authenticationRequest), HttpStatus.BAD_REQUEST);
        }
        String token =generateToken(user);

        return new ResponseEntity<>(new ResponseObject("true", "Đăng nhập thành công", 0, token), HttpStatus.OK);
    }

    public void logout(LogoutRequest request) throws Exception {
        SignedJWT signToken = verifyToken(request.getToken());

        String jit =signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =InvalidatedToken.builder()
                .idCode(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws Exception {
        //tạo một đối tượng JWSVerifier để xác minh chữ ký của token. Trong trường hợp này, nó sử dụng MACVerifier,
        // một loại JWSVerifier dựa trên mã hóa MAC (Message Authentication Code), và cung
        // cấp khóa để xác thực (SIGNER_KEY).
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT =SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

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
        var signedJWT =verifyToken(request.getToken());

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

        return new ResponseEntity<>(new ResponseObject("true", "Tài khoản đúng", 0, token), HttpStatus.OK);
    }

    private String generateToken(Users users){
        JWSHeader header =new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet =new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(30, ChronoUnit.DAYS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(users))
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
