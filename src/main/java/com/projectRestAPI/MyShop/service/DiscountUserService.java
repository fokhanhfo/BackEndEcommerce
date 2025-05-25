package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserAllRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;


public interface DiscountUserService extends BaseService<DiscountUser,Long> {
    ResponseEntity<ResponseObject> add(DiscountUserRequest discountRequest);

    ResponseEntity<ResponseObject> addAll(DiscountUserAllRequest discountUserAllRequest);

    //    ResponseEntity<ResponseObject> getAll();
//
    ResponseEntity<ResponseObject> getId(Long id);
    //
    ResponseEntity<ResponseObject> update(DiscountUserRequest discountRequest);
    //
//    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);
//
    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);

    ResponseEntity<ResponseObject> getAllDiscountByUser(Boolean isUsed);

    ResponseEntity<ResponseObject> deleteDiscount(Long id);
}
