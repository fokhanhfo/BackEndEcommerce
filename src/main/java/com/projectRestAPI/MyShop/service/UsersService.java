package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService extends BaseService<Users,Long>{

    public ResponseEntity<?> getAll();

    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
    public boolean isUserExists(String UserName);

    public ResponseEntity<?> getMyInfo();

    ResponseEntity<ResponseObject> addUser(UserRequest userRequest);

    public Users getUser();

    public UserResponse validUser(Users users);
}
