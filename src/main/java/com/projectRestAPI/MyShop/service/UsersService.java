package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService extends BaseService<Users,Long>{

    public ResponseEntity<?> getAll();
    public boolean isUserExists(String UserName);

    public ResponseEntity<?> getMyInfo();

    ResponseEntity<ResponseObject> addUser(UserRequest userRequest);

    public Users getUser();

    public UserResponse validUser(Users users);
}
