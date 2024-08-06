package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.request.UserRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.dto.response.UserResponse;
import com.projectRestAPI.studensystem.enums.Role;
import com.projectRestAPI.studensystem.model.Roles;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.RolesRepository;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import com.projectRestAPI.studensystem.service.RolesService;
import com.projectRestAPI.studensystem.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    private UsersRepository usersRepository;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        log.info("in method get user");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}",authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<Users> users=usersService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
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

    @GetMapping("myInfo")
    public ResponseEntity<?> getMyInFo(){
        return usersService.getMyInfo();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserRequest userRequest){
        if(usersService.isUserExists(userRequest.getUsername())){
            return new ResponseEntity<>(new ResponseObject("Fail", "Trùng Username", 1, null), HttpStatus.BAD_REQUEST);
        }
        Users users = mapper.map(userRequest, Users.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        List<Roles> roles = new ArrayList<>();
        roles.add(rolesService.findById(2L).get());
        users.setRoles(roles);
        return usersService.createNew(users);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest,@PathVariable("id") Long id){
        Users user =null;
        Optional<Users> opt = usersService.findById(id);
        if(opt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("Fail", "Không Thấy ID", 1, userRequest), HttpStatus.BAD_REQUEST);
        }
        if(opt.isPresent()) {
            user =mapper.map(userRequest, Users.class);
            user.setId(id);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setRoles(rolesService.findAllById(userRequest.getRoles()));
            return usersService.update(user);
        }
        return new ResponseEntity<>(new ResponseObject("Fail", "Không Update được", 1, userRequest), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return usersService.delete(id);
    }

}
