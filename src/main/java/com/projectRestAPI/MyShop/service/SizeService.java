package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface SizeService extends BaseService<Size,Long> {
    ResponseEntity<ResponseObject> add(SizeRequest sizeRequest);

//    ResponseEntity<ResponseObject> getAll();
//
    ResponseEntity<ResponseObject> getId(Long id);
//
    ResponseEntity<ResponseObject> update(SizeRequest sizeRequest);
//
//    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);
//
    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
//
//    ResponseEntity<ResponseObject> getCount();
//
//    ResponseEntity<ResponseObject> getNewProduct(Pageable pageable);
}
