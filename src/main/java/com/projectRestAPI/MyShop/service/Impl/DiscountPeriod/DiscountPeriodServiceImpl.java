package com.projectRestAPI.MyShop.service.Impl.DiscountPeriod;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountPeriodRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.DiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.DiscountPeriodService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountPeriodServiceImpl extends BaseServiceImpl<DiscountPeriod,Long, DiscountPeriodRepository> implements DiscountPeriodService {
    @Override
    public ResponseEntity<ResponseObject> add(DiscountPeriod discountPeriod) {
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<DiscountPeriod> discount = findById(id);
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
    public ResponseEntity<ResponseObject> update(DiscountPeriod discountPeriod) {
        Optional<DiscountPeriod> discountOptional = findById(discountPeriod.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

    @Override
    public void setRepository(DiscountPeriodRepository repository) {
        super.setRepository(repository);
    }
}
