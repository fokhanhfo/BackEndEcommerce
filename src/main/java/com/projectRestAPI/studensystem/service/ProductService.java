package com.projectRestAPI.studensystem.service;


import com.projectRestAPI.studensystem.dto.param.ProductParam;
import com.projectRestAPI.studensystem.dto.request.ProductRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService extends BaseService<Product,Long> {
    ResponseEntity<ResponseObject> addProduct(ProductRequest productRequest);

    ResponseEntity<ResponseObject> getAll();

    ResponseEntity<ResponseObject> getId(Long id);

    ResponseEntity<ResponseObject> updateProduct(Long id,ProductRequest productRequest);

    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);

    ResponseEntity<ResponseObject> getAllProduct(Pageable pageable , ProductParam productParam);

    ResponseEntity<ResponseObject> getCount();

    ResponseEntity<ResponseObject> getNewProduct(Pageable pageable);


}
