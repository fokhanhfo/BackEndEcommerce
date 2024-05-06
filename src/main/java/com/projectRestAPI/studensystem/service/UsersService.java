package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.model.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService extends BaseService<Users,Long>{
    public boolean isUserExists(String UserName);

    public ResponseEntity<?> getMyInfo();

    public Users getUser();
}
