package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.response.CartResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.StatusCart;
import com.projectRestAPI.MyShop.mapper.CartMapper;
import com.projectRestAPI.MyShop.mapper.ColorMapper;
import com.projectRestAPI.MyShop.mapper.SizeMapper;
import com.projectRestAPI.MyShop.model.*;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.repository.CartRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailRepository;
import com.projectRestAPI.MyShop.repository.ProductRepository;
import com.projectRestAPI.MyShop.service.CartService;
import com.projectRestAPI.MyShop.service.ProductDetailService;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.service.UsersService;
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
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeMapper sizeMapper;

    private final String UrlBase="http://localhost:8080/image/";

    @Override
    public ResponseEntity<ResponseObject> getCart() {
        Users users = usersService.getUser();
        List<Cart> carts = repository.findCartByUser(users.getId());
        if(carts.isEmpty()){
            return new ResponseEntity<>(new ResponseObject(null,"Giỏ hàng trống",200,null), HttpStatus.OK);
        }
        List<CartResponse> cartResponses = cartMapper.toListCartResponse(carts);
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
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(cartRequest.getProductDetail().getId());
        Optional<Cart> product_Cart = repository.findCartProduct(cartRequest.getProductDetail().getId(),user.getId());
        if (product_Cart.isEmpty() && productDetailOptional.isPresent()) {
            ProductDetail productDetail = productDetailOptional.get();
//            if(product.getStatus() == 0 ){
//                return new ResponseEntity<>(new ResponseObject("Error","Sản phẩm đã ngừng bán" + product.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
//            }
//            if (cartRequest.getQuantity()>product.getQuantity()){
//                return new ResponseEntity<>(new ResponseObject("0","Số lượng phải nhỏ hơn " + product.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
//            }
            Cart cart = Cart.builder()
                    .productDetail(productDetail)
                    .quantity(cartRequest.getQuantity())
                    .user(user)
                    .color(colorMapper.toColor(cartRequest.getColor()))
                    .size(sizeMapper.toSize(cartRequest.getSize()))
                    .status(StatusCart.CART_NOT_SELECT.getStatus())
                    .build();
            createNew(cart);
            CartResponse cartResponse = cartMapper.toCartResponse(cart);
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
        ProductDetail productDetail = productDetailRepository.findById(cart.getProductDetail().getId()).get();
//        if(productDetail.getQuantity() < cartRequest.getQuantity()){
//            return new ResponseEntity<>(new ResponseObject("0","Sản phẩm còn " + productDetail.getQuantity(),400,null), HttpStatus.BAD_REQUEST);
//        }
        cart.setQuantity(cartRequest.getQuantity());
        cart.setStatus(cartRequest.getStatus());
        update(cart);
        return new ResponseEntity<>(new ResponseObject("Succes","Cập nhật số lượng thành công",200,cartMapper.toCartResponse(cart)), HttpStatus.OK);
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
//        List<Image> images = cart.getProduct().getImages();
        List<String> urlImage = new ArrayList<>();
//        urlImage.add(UrlBase+images.get(0).getName());
//        cartResponse.getProduct().setImagesUrl(urlImage);
        return  cartResponse;
    }
}
