package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface ColorService extends BaseService<Color,Long> {
    ResponseEntity<ResponseObject> add(ColorRequest solorRequest);

    //    ResponseEntity<ResponseObject> getAll();
//
    ResponseEntity<ResponseObject> getId(Long id);
    //
    ResponseEntity<ResponseObject> update(ColorRequest solorRequest);
    //
//    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);
//
    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
}
