package com.projectRestAPI.MyShop.service.Impl.Discount;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountUserMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.service.DiscountUserService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountUserServiceImpl extends BaseServiceImpl<DiscountUser,Long, DiscountUserRepository> implements DiscountUserService {
    @Autowired
    private DiscountUserMapper discountUserMapper;

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
}
