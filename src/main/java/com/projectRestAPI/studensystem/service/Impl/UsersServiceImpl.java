package com.projectRestAPI.studensystem.service.Impl;


import com.projectRestAPI.studensystem.dto.request.UserRequest;
import com.projectRestAPI.studensystem.dto.response.BillResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.dto.response.UserResponse;
import com.projectRestAPI.studensystem.enums.LoginType;
import com.projectRestAPI.studensystem.enums.Role;
import com.projectRestAPI.studensystem.model.Roles;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.RolesRepository;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import com.projectRestAPI.studensystem.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UsersServiceImpl extends BaseServiceImpl<Users,Long, UsersRepository> implements UsersService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public boolean isUserExists(String UserName) {
        return repository.existsByUsername(UserName);
    }

    protected static final List<String> ROLE_NAMES = Arrays.asList("ADMIN", "MANAGER", "EMPLOYEE");

    public boolean userHasRoleInList(List<String> userRoles) {
        return userRoles.stream()
                .anyMatch(ROLE_NAMES::contains);
    }

    @Override
    public ResponseEntity<ResponseObject> addUser(UserRequest userRequest) {
        if(isUserExists(userRequest.getUsername())){
            return new ResponseEntity<>(new ResponseObject("Fail", "Trùng Username", 1, null), HttpStatus.BAD_REQUEST);
        }
        Users users = mapper.map(userRequest, Users.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        users.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Map<Integer, LoginType> loginTypeMap = Map.of(
                0, LoginType.NORMAL_LOGIN,
                1, LoginType.FACEBOOK_LOGIN,
                2, LoginType.GOOGLE_LOGIN,
                3, LoginType.GITHUB_LOGIN
        );

        LoginType selectedLoginType = loginTypeMap.get(userRequest.getTypeLogin());
        users.setTypeLogin(selectedLoginType != null ? selectedLoginType.getLoginType() : null);
        List<Roles> roles = new ArrayList<>();
        roles.add(rolesRepository.findByName(Role.USER.getRoles()));
        users.setRoles(roles);
        return createNew(users);
    }

    @Override
    public ResponseEntity<?> getMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> usersOpt = repository.findByUsername(name);
        if(usersOpt.isPresent()){
            Users user =usersOpt.get();
            UserResponse userResponse = validUser(user);
            return new ResponseEntity<>(new ResponseObject("200","Lấy dữ liệu thành công",1,userResponse) , HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @Override
    public Users getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        return repository.findByUsername(name)
                .orElse(null);
    }

    public UserResponse validUser(Users users) {
        UserResponse userResponse = mapper.map(users, UserResponse.class);

        List<String> listRoleName = users.getRoles().stream()
                .map(Roles::getName)
                .toList();

        if (listRoleName.stream().noneMatch(ROLE_NAMES::contains)) {
            userResponse.setPassword(null);
        }

        return userResponse;
    }



}
