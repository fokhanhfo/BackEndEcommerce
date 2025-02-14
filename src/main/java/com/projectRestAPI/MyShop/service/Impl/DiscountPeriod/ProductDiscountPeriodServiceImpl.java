package com.projectRestAPI.MyShop.service.Impl.DiscountPeriod;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDisCountPeriod;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.DiscountPeriodRepository;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.ProductDiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.DiscountPeriodService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductDiscountPeriodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDiscountPeriodServiceImpl extends BaseServiceImpl<ProductDisCountPeriod,Long, ProductDiscountPeriodRepository> implements ProductDiscountPeriodService {
    @Override
    public ResponseEntity<ResponseObject> add(ProductDisCountPeriod discountPeriod) {
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<ProductDisCountPeriod> discount = findById(id);
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
    public ResponseEntity<ResponseObject> update(ProductDisCountPeriod discountPeriod) {
        Optional<ProductDisCountPeriod> discountOptional = findById(discountPeriod.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }
}
