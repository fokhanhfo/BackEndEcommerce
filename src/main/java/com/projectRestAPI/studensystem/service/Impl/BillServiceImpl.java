package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.BillDetailResponse;
import com.projectRestAPI.studensystem.dto.response.BillResponse;
import com.projectRestAPI.studensystem.dto.response.ProductResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.*;
import com.projectRestAPI.studensystem.repository.BillRepository;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;
    private final String UrlBase="http://localhost:8080/image/";

    @Override
    public ResponseEntity<?> saveBill(BillRequest billRequest) {
        Users user = usersService.getUser();
        List<Cart> carts = billRequest.getCartRequests().stream()
                .map(cartRequest -> cartService.findById(cartRequest.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND)))
                .toList();
        for (Cart cart : carts){
            if (cart.getUser() != user){
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        Bill bill = mapper.map(billRequest,Bill.class);
        bill.setStatus(0);
        bill.setTotal_price(totalPrice(billRequest.getCartRequests()));
        bill.setUser(user);
        bill.setCreatedDate(LocalDateTime.now());
        List<BillDetail> billDetails = billRequest.getCartRequests().stream().map(cartRequest -> saveBillDetail(bill,cartRequest)).toList();
        bill.setBillDetail(billDetails);
        createNew(bill);
        BillResponse billResponse = mapToResponse(bill);
        return new ResponseEntity<>(new ResponseObject("Succes","Lập hóa đơn thành công",200,billResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getBillId(Long id){
        Bill bill = findById(id).orElseThrow(()->new AppException(ErrorCode.BILL_NOT_FOUND));
        BillResponse billResponse = mapToResponse(bill);
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy hóa đơn thành công",200,billResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getBillIdUser(){
        Users user = usersService.getUser();
        List<Bill> bills = repository.findByUser(user);
        List<BillResponse> billResponses= bills.stream().map(this::mapToResponse).toList();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy hóa đơn thành công",200,billResponses), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> UpdateStatus(Long bill_id,Integer status){
        Bill bill = findById(bill_id).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        bill.setStatus(status);
        createNew(bill);
        BillResponse billResponse = mapToResponse(bill);
        return new ResponseEntity<>(new ResponseObject("Succes","Cập nhật trạng thái hóa đơn thành công",200,billResponse), HttpStatus.OK);
    }

    private BillDetail saveBillDetail(Bill bill,CartRequest cartRequest){
        Product product = productRepository.findById(cartRequest.getProduct()).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        BillDetail billDetail = BillDetail.builder()
                .productId(product)
                .quantity(cartRequest.getQuantity())
                .bill(bill)
                .build();
        cartService.delete(cartRequest.getId());
        product.setQuantity(product.getQuantity()-cartRequest.getQuantity());
        productService.createNew(product);
        return billDetail;
    }

    private BigDecimal totalPrice(List<CartRequest> cartRequests){
        BigDecimal total = BigDecimal.valueOf(0);
        for (CartRequest cartRequest : cartRequests){
            Product product = productRepository.findById(cartRequest.getProduct()).get();
            total = product.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity()));
        }
        return total;
    }

    private BillResponse mapToResponse(Bill bill){
        BillResponse billResponse = mapper.map(bill,BillResponse.class);
        billResponse.setUser_id(bill.getUser().getId());

        List<BillDetail> billDetails = bill.getBillDetail();
        List<Product> products = billDetails.stream().map(BillDetail::getProductId).toList();
        List<BillDetailResponse> billDetailResponses = billResponse.getBillDetail();

        for (BillDetailResponse billDetailResponse : billDetailResponses){
            Product product =findProductById(products,billDetailResponse.getProductId().getId());
            if(product != null){
                List<String> imageUrl = product.getImages().stream().map(image -> UrlBase+image.getName())
                        .toList();
                billDetailResponse.getProductId().setImagesUrl(imageUrl);
            }
        }

        return billResponse;
    }

    private Product findProductById(List<Product> products,Long productId){
        return products.stream().filter(product -> product.getId().equals(productId)).findFirst().orElse(null);
    }

}
