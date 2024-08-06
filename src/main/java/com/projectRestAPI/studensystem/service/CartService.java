package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService extends BaseService<Cart,Long>{
    public ResponseEntity<ResponseObject> getCart();

    public ResponseEntity<ResponseObject> AddCart(CartRequest cartRequest);

    public ResponseEntity<ResponseObject> UpdateQuantityCart(Long id,CartRequest cartRequest);

    public ResponseEntity<ResponseObject> deleteAll();

    ResponseEntity<ResponseObject> deleteProductCart(Long id);
}
