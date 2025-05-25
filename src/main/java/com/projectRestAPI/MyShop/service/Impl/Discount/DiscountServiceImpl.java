package com.projectRestAPI.MyShop.service.Impl.Discount;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountMapper;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountShortMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.Discount.DiscountRepository;
import com.projectRestAPI.MyShop.service.DiscountService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl extends BaseServiceImpl<Discount,Long, DiscountRepository> implements DiscountService {
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private DiscountShortMapper discountShortMapper;

    @Autowired
    private UsersService usersService;

    @Override
    public ResponseEntity<ResponseObject> add(DiscountRequest discountRequest) {
        Discount discount = discountMapper.toDiscount(discountRequest);
        return createNew(discount);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<Discount> discount = findById(id); 
        if(discount.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        DiscountResponse discountResponse = discountShortMapper.toDiscountResponse(discount.get());
        return new ResponseEntity<>(new ResponseObject(
                "success",
                "Lấy Thành Công",
                1,
                discountResponse
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(DiscountRequest discountRequest) {
        Optional<Discount> discountOptional = findById(discountRequest.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        Discount discount = discountMapper.toDiscount(discountRequest);
        return createNew(discount);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllUser() {
        Users users = usersService.getUser();
        return null;
    }
}
