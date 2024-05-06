package com.projectRestAPI.studensystem.controller;

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
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getCart(){
        List<Cart> carts = cartService.getCart();
        List<CartResponse> cartResponses = new ArrayList<>();
        for(Cart cart : carts){
            CartResponse cartResponse= CartResponse.builder()
                    .id(cart.getId())
                    .product(productRepository.findById(cart.getProduct().getId()).orElse(null))
                    .quantity(cart.getQuantity())
                    .build();
            cartResponses.add(cartResponse);
        }
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CartRequest cartRequest) {
        Users user = usersService.getUser();
        Optional<Product> productOptional = productService.findById(cartRequest.getProduct());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Cart cart = Cart.builder()
                    .product(product)
                    .quantity(cartRequest.getQuantity())
                    .user(user)
                    .build();
            return cartService.createNew(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody CartRequest cartRequest){
        Optional<Cart> cartOptional = cartService.findById(id);
        if(cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            cart.setQuantity(cartRequest.getQuantity());
            return cartService.update(cart);
        }
        return new ResponseEntity<>(new ResponseObject("error","Udate không thành công",0,cartRequest),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return cartService.delete(id);
    }
}
