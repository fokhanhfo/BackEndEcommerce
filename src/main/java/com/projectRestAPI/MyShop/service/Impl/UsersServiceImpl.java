package com.projectRestAPI.MyShop.service.Impl;


import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.enums.LoginType;
import com.projectRestAPI.MyShop.enums.Role;
import com.projectRestAPI.MyShop.mapper.UserMapper;
import com.projectRestAPI.MyShop.model.Roles;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.RolesRepository;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UsersServiceImpl extends BaseServiceImpl<Users,Long, UsersRepository> implements UsersService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<?> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}",authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<Users> users=repository.findAll();
        List<UserResponse> userResponses = userMapper.toListUserResponse(users);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(userResponses)
                .build();
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        Page<Users> response = getAll(params,pageable,sort,null);
        List<Users> users = response.getContent();
        List<UserResponse> userResponses =userMapper.toListUserResponse(users);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("users",userResponses);
                    put("count",response.getTotalElements());
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

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

        return repository.findByUsername(name)
                .orElse(null);
    }

    public UserResponse validUser(Users users) {
        UserResponse userResponse = mapper.map(users, UserResponse.class);

        List<String> listRoleName = users.getRoles().stream()
                .map(Roles::getName)
                .toList();


        return userResponse;
    }



}
