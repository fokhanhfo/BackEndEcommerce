package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.RolesRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.service.RolesService;
import com.projectRestAPI.MyShop.service.UsersService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private DiscountUserRepository discountUserRepository;

//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getAll(){
//        return usersService.getAll();
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    //idDiscount lọc ra các user chưa có của discount đấy
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "idDiscount",defaultValue = "")Long idDiscount,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit,
                                    @RequestParam(value = "sort",defaultValue = "") String sort){
        Pageable pageable = PageRequest.of(page,limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (idDiscount != null) {
            List<Long> userIds = discountUserRepository.findUserIdsByDiscountId(idDiscount);

            if (!userIds.isEmpty()) {
                criteriaList.add(new SearchCriteria("id", "notIn", userIds));
            }
        }
        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return usersService.getAll(criteriaList,pageable,sortParams);
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
        return usersService.addUser(userRequest);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest,@PathVariable("id") Long id){
        Users user =null;
        Optional<Users> opt = usersService.findById(id);
        if(opt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("Fail", "Không Thấy ID", 1, userRequest), HttpStatus.BAD_REQUEST);
        }
        user =mapper.map(userRequest, Users.class);
        user.setId(id);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(rolesService.findAllById(userRequest.getRoles().stream().map(RolesRequest::getId).toList()));
        return usersService.update(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return usersService.delete(id);
    }

}
