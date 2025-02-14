package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDisCountPeriod;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductDiscountPeriodService extends BaseService<ProductDisCountPeriod,Long>{
    ResponseEntity<ResponseObject> add(ProductDisCountPeriod disCountPeriod);

    ResponseEntity<ResponseObject> getId(Long id);

    ResponseEntity<ResponseObject> update(ProductDisCountPeriod disCountPeriod);

    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
}
