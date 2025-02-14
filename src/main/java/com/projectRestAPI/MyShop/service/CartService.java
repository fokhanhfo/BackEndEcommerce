package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Cart;
import org.springframework.http.ResponseEntity;

public interface CartService extends BaseService<Cart,Long>{
    public ResponseEntity<ResponseObject> getCart();

    public ResponseEntity<ResponseObject> AddCart(CartRequest cartRequest);

    public ResponseEntity<ResponseObject> UpdateQuantityCart(Long id,CartRequest cartRequest);

    public ResponseEntity<ResponseObject> deleteAll();

    ResponseEntity<ResponseObject> deleteProductCart(Long id);
}
