package com.projectRestAPI.MyShop.service.Impl.Discount;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserAllRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountUserResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.DiscountStatus;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountMapper;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountUserMapper;
import com.projectRestAPI.MyShop.mapper.UserMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.service.DiscountUserService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.UsersService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountUserServiceImpl extends BaseServiceImpl<DiscountUser,Long, DiscountUserRepository> implements DiscountUserService {
    @Autowired
    private DiscountUserMapper discountUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscountMapper discountMapper;

    @Autowired
    private UsersService usersService;

    @Override
    public ResponseEntity<ResponseObject> add(DiscountUserRequest discountUserRequest) {
        DiscountUser discountUser = discountUserMapper.toDiscountUser(discountUserRequest);
        return createNew(discountUser);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<DiscountUser> discount = findById(id);
        if(discount.isEmpty()){
            throw new AppException(ErrorCode.DISCOUNT_NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseObject(
                "success",
                "Lấy Thành Công",
                1,
                discount.get()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(DiscountUserRequest discountRequest) {
        Optional<DiscountUser> discountOptional = findById(discountRequest.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        DiscountUser discount = discountUserMapper.toDiscountUser(discountRequest);
        return createNew(discount);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> addAll(DiscountUserAllRequest discountUserAllRequest) {
        List<DiscountUser> discountUsers = discountUserAllRequest.getUsers().stream().map((item)->{
            DiscountUser discountUser = DiscountUser.builder()
                    .users(userMapper.toUsers(item))
                    .discount(discountMapper.toDiscount(discountUserAllRequest.getDiscount()))
                    .isUsed(false)
                    .status(DiscountStatus.INACTIVE.getValue())
                    .build();
            return discountUser;
        }).toList();
        List<DiscountUser> discountUsers1 = repository.saveAll(discountUsers);
        return ResponseUtil.buildResponse("success","Thêm thành công",1,discountUsers1,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteDiscount(Long id) {
        Optional<DiscountUser> otp = findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllDiscountByUser(Boolean isUsed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Boolean result;
        if(authorities.size() == 1 &&
                "ROLE_USER".equals(authorities.iterator().next().getAuthority())){
            result = false;
        }else{
            result = isUsed;
        }
        Users user = usersService.getUser();
        List<DiscountUser> discountByUserId = repository.findDiscountUserByUserId(user.getId(),result);
        List<DiscountUserResponse> discountUserResponses= discountUserMapper.toListDiscountUserResponse(discountByUserId);
        return ResponseUtil.buildResponse("success" , "Lấy dữ lệu thành công",1,discountUserResponses,HttpStatus.OK);
    }
}
