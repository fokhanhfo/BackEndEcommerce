package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountPeriodRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface DiscountPeriodService extends BaseService<DiscountPeriod,Long>{
    ResponseEntity<ResponseObject> add(DiscountPeriod discountPeriod);

    ResponseEntity<ResponseObject> getId(Long id);

    ResponseEntity<ResponseObject> update(DiscountPeriod discountPeriod);

    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
}
