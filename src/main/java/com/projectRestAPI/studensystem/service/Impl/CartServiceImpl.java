package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.BillResponse;
import com.projectRestAPI.studensystem.dto.response.CartResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.enums.StatusCart;
import com.projectRestAPI.studensystem.model.*;
import com.projectRestAPI.studensystem.repository.CartRepository;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.CartService;
import com.projectRestAPI.studensystem.service.ProductService;
import com.projectRestAPI.studensystem.service.UsersService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl extends BaseServiceImpl<Cart,Long, CartRepository> implements CartService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private final String UrlBase="http://localhost:8080/image/";

    @Override
    public ResponseEntity<ResponseObject> getCart() {
        Users users = usersService.getUser();
        List<Cart> carts = repository.findCartByUser(users.getId());
        if(carts.isEmpty()){
            return new ResponseEntity<>(new ResponseObject(null,"Giỏ hàng trống",200,null), HttpStatus.OK);
        }
        List<CartResponse> cartResponses = carts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseObject("200","Get Secces",1,cartResponses), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> deleteAll() {
        Users users = usersService.getUser();
        repository.deleteByUser(users.getId());
        return new ResponseEntity<>(new ResponseObject("200","Xóa thành công",1,null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> AddCart(CartRequest cartRequest) {
        Users user = usersService.getUser();
        Optional<Product> productOptional = productRepository.findById(cartRequest.getProduct());
        Optional<Cart> product_Cart = repository.findCartProduct(cartRequest.getProduct(),user.getId());
        if (product_Cart.isEmpty() && productOptional.isPresent()) {
            Product product = productOptional.get();
            if(product.getStatus() == 0 ){
                return new ResponseEntity<>(new ResponseObject("Error","Sản phẩm đã ngừng bán" + product.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
            }
            if (cartRequest.getQuantity()>product.getQuantity()){
                return new ResponseEntity<>(new ResponseObject("0","Số lượng phải nhỏ hơn " + product.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
            }
            Cart cart = Cart.builder()
                    .product(product)
                    .quantity(cartRequest.getQuantity())
                    .user(user)
                    .status(StatusCart.CART_NOT_SELECT.getStatus())
                    .build();
            createNew(cart);
            CartResponse cartResponse = mapToResponse(cart);
            return new ResponseEntity<>(new ResponseObject("success","Thêm thành công",0,cartResponse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject("error","Sản phẩm đã có trong giỏ hàng",400,null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> UpdateQuantityCart(Long id, CartRequest cartRequest) {
        Users user = usersService.getUser();
        Optional<Cart> cartOptional = repository.findById(id);
        List<Cart> carts = repository.findCartByUser(user.getId());
        if (cartOptional.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error","Cart not found",400,null), HttpStatus.BAD_REQUEST);
        }
        Cart cart = cartOptional.get();

        if (!carts.contains(cart)){
            return new ResponseEntity<>(new ResponseObject("error", "Sản phẩm không tồn tại trong giỏ hàng user", 400, null), HttpStatus.BAD_REQUEST);
        }
        Product product = productService.findById(cart.getProduct().getId()).get();
        if(product.getQuantity() < cartRequest.getQuantity()){
            return new ResponseEntity<>(new ResponseObject("0","Sản phẩm còn " + product.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
        }
        cart.setQuantity(cartRequest.getQuantity());
        cart.setStatus(cartRequest.getStatus());
        update(cart);
        return new ResponseEntity<>(new ResponseObject("Succes","Cập nhật số lượng thành công",200,mapToResponse(cart)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProductCart(Long id){
        Users user = usersService.getUser();
        Optional<Cart> cartOptional = findById(id);
        if(cartOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        Cart cart = cartOptional.get();
        if(user == cart.getUser()){
            delete(id);
            return new ResponseEntity<>(new ResponseObject("Succes", "Xóa thành công", 200, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseObject("Error", "Xóa không thành công", 400, null), HttpStatus.BAD_REQUEST);
    }


    private CartResponse mapToResponse(Cart cart){
        CartResponse cartResponse = mapper.map(cart,CartResponse.class);
        List<Image> images = cart.getProduct().getImages();
        List<String> urlImage = new ArrayList<>();
        urlImage.add(UrlBase+images.get(0).getName());
        cartResponse.getProduct().setImagesUrl(urlImage);
        return  cartResponse;
    }
}
