package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.UserRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.dto.response.UserResponse;
import com.projectRestAPI.studensystem.model.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService extends BaseService<Users,Long>{
    public boolean isUserExists(String UserName);

    public ResponseEntity<?> getMyInfo();

    ResponseEntity<ResponseObject> addUser(UserRequest userRequest);

    public Users getUser();

    public UserResponse validUser(Users users);
}
