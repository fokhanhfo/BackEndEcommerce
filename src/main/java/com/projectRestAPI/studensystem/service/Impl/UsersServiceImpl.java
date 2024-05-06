package com.projectRestAPI.studensystem.service.Impl;


import com.projectRestAPI.studensystem.dto.response.UserResponse;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import com.projectRestAPI.studensystem.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl extends BaseServiceImpl<Users,Long, UsersRepository> implements UsersService {
    @Autowired
    private ModelMapper mapper;
    @Override
    public boolean isUserExists(String UserName) {
        return repository.existsByUsername(UserName);
    }

    @Override
    public ResponseEntity<?> getMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> usersOpt = repository.findByUsername(name);
        if(usersOpt.isPresent()){
            Users user =usersOpt.get();
            UserResponse userResponse = mapper.map(user, UserResponse.class);
            return new ResponseEntity<>(userResponse , HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @Override
    public Users getUser() {
        return repository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(null);
    }

}
