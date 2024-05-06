package com.projectRestAPI.studensystem.service;


import com.projectRestAPI.studensystem.dto.request.ProductRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService extends BaseService<Product,Long> {
//    ResponseEntity<ResponseObject> save(ProductRequest request);
    public Product save(Product product);

    public int getQuantity(Long id);

    public Page<Product> getAllProduct(Pageable pageable , Long id);

}
