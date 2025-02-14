package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ResponseObject> getCart(){
        return cartService.getCart();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody CartRequest cartRequest) {
        return cartService.AddCart(cartRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody CartRequest cartRequest){
        return cartService.UpdateQuantityCart(id,cartRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteId(@PathVariable Long id){
        return cartService.deleteProductCart(id);
    }

    @DeleteMapping
    public  ResponseEntity<ResponseObject> deleteAll(){
        return cartService.deleteAll();
    }
}
