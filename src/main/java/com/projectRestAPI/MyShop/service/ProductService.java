package com.projectRestAPI.MyShop.service;


import com.projectRestAPI.MyShop.dto.param.ProductParam;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product,Long> {
    ResponseEntity<ResponseObject> addProduct(ProductRequest productRequest,MultiValueMap<String, MultipartFile> productDetails);

    ResponseEntity<ResponseObject> getAll();

    ResponseEntity<ResponseObject> getId(Long id);

    ResponseEntity<ResponseObject> update(ProductRequest productRequest, MultiValueMap<String, MultipartFile> productDetails);

    ResponseEntity<ResponseObject> updateStatus(Long id,Integer status);

    ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);

    ResponseEntity<ResponseObject> getCount();

    ResponseEntity<ResponseObject> getNewProduct(Pageable pageable);


}
