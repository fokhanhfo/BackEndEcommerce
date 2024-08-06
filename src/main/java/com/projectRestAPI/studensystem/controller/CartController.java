package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.CartResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Cart;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.CartService;
import com.projectRestAPI.studensystem.service.ProductService;
import com.projectRestAPI.studensystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
