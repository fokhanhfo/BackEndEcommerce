package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.ChangePasswordRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsersService extends BaseService<Users,Long>{

    public ResponseEntity<?> getAll();

    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
    public boolean isUserExists(String UserName);

    public ResponseEntity<?> getMyInfo();

    ResponseEntity<ResponseObject> addUser(UserRequest userRequest, MultipartFile file);

    ResponseEntity<ResponseObject> requestChangePassword(ChangePasswordRequest request);

    public ResponseEntity<ResponseObject> confirmChangePassword(ChangePasswordRequest request);

    ResponseEntity<ResponseObject> updateUser(UserRequest userRequest, MultipartFile file);

    public Users getUser();

    public UserResponse validUser(Users users);

    ResponseEntity<ResponseObject> verifyOtp(String email, String otp, String purpose);
    ResponseEntity<ResponseObject> resendOtp(String email,String purpose);

    ResponseEntity<ResponseObject> getUserStatisticsByMonth();

    ResponseEntity<ResponseObject> confirmForgotPassword(ChangePasswordRequest request);
    ResponseEntity<ResponseObject> forgotPassword(String email);
}
