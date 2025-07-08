package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DiscountService extends BaseService<Discount,Long> {
    ResponseEntity<ResponseObject> add(DiscountRequest discountRequest);

    ResponseEntity<ResponseObject> getAllUser();
//
    ResponseEntity<ResponseObject> getId(Long id);
    //
    ResponseEntity<ResponseObject> update(DiscountRequest discountRequest);
    //
    ResponseEntity<ResponseObject> deleteDiscount(Long id);
//
    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);

    ResponseEntity<ResponseObject> updateStatus(DiscountRequest discountRequest);
}
