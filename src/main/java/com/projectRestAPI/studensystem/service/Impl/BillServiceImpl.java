package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.model.Bill;
import com.projectRestAPI.studensystem.model.BillDetail;
import com.projectRestAPI.studensystem.model.Cart;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.BillRepository;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.BillDetailService;
import com.projectRestAPI.studensystem.service.BillService;
import com.projectRestAPI.studensystem.service.CartService;
import com.projectRestAPI.studensystem.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl extends BaseServiceImpl<Bill,Long, BillRepository> implements BillService {
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    private int getQuantity(Long id){
        return productRepository.findQuantityById(id);
    }
    @Override
    public ResponseEntity<?> updateStatusBill() {
        return null;
    }

    @Override
    public ResponseEntity<?> saveBill(BillRequest billRequest) {
        Users users = usersService.getUser();
        List<Cart> carts = cartService.getCart();
        List<BillDetail> billDetails = new ArrayList<>();
        BigDecimal total_cart = BigDecimal.valueOf(0);
        for (Cart cart : carts) {
            Long product_id = cart.getProduct().getId();
            total_cart = total_cart.add(BigDecimal.valueOf(cart.getQuantity()).multiply(cart.getProduct().getPrice()));
            BillDetail billDetail=BillDetail.builder()
                    .quantity(cart.getQuantity())
                    .productId(productRepository.findById(product_id).orElse(null))
                    .build();
            billDetails.add(billDetail);
            if(getQuantity(product_id)==0){
                return new ResponseEntity<>("Sản phẩm hết hàng", HttpStatus.BAD_REQUEST);
            }
            if (cart.getQuantity()>getQuantity(product_id)) {
                return new ResponseEntity<>("Sản phẩm "+ cart.getProduct().getName()+" còn :"+cart.getQuantity(),HttpStatus.BAD_REQUEST);
            }
            productRepository.updateQuantityById(product_id,(getQuantity(product_id)-cart.getQuantity()));
        }
        billDetailService.saveAllBillDetail(billDetails);
        Bill bill = Bill.builder()
                .address(billRequest.getAddress())
                .email(billRequest.getEmail())
                .phone(billRequest.getPhone())
                .status(1)
                .total_price(total_cart)
                .billDetail(billDetails)
                .user(users)
                .build();
        cartService.deleteAll();
        return createNew(bill);
    }
}
