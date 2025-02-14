package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.ProductDetailRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductDetailService {
    ResponseEntity<ResponseObject> add(ProductDetailRequest productDetailRequest);

    //    ResponseEntity<ResponseObject> getAll();
//
    ResponseEntity<ResponseObject> getId(Long id);
    //
    ResponseEntity<ResponseObject> update(ProductDetailRequest productDetailRequest);
    //
//    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);
//
    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
}
