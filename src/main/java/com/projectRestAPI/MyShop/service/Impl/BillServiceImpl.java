package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.param.BillParam;
import com.projectRestAPI.MyShop.dto.request.BillRequest;
import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.*;
import com.projectRestAPI.MyShop.model.*;
import com.projectRestAPI.MyShop.repository.BillRepository;
import com.projectRestAPI.MyShop.repository.ProductRepository;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper mapper;
    private final String UrlBase="http://localhost:8080/image/";
    // biến ROLE_NAME cần cho vào aplication
    protected static final List<String> ROLE_NAMES = Arrays.asList("ADMIN", "MANAGER", "EMPLOYEE");

//    @Override
//    public ResponseEntity<ResponseObject> getAll(Pageable pageable, BillParam billParam) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAdmin = authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
//        Long idUser = null;
//        if(!isAdmin){
//            idUser = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId();
//        }
//        Page<Bill> billPageResponse = repository.findBillsByCriteria(
//                billParam.getStartDate(),
//                billParam.getEndDate(),
//                billParam.getSearch(),
//                billParam.getStatus(),
//                idUser,
//                pageable
//        );
////        Page<Bill> billPage = billPageResponse.getBillPage();
//        Long count = billPageResponse.getTotalElements();
//        List<Bill> bills = billPageResponse.getContent();
//        List<BillResponse> billResponses = bills.stream().map(this::mapToResponse).toList();
////        Long totalRowBill = billPageResponse.getTotalRows();
//        ResponseObject responseObject = ResponseObject.builder()
//                .status("Success")
//                .message("Lấy dữ liệu thành công")
//                .errCode(200)
//                .data(new HashMap<String,Object>(){{
//                    put("bill",billResponses);
//                    put("count",count);
//                }})
//                .build();
//        return new ResponseEntity<>(responseObject, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersRepository.findByUsername(userName).orElseThrow(()->
                new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Page<Bill> response = getAll(params, pageable, sort, users);
        List<BillResponse> billResponses = response.getContent().stream().map(this::mapToResponse).toList();
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("bill",billResponses);
                    put("count",response.getTotalElements());
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

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
//        bill.setTotal_price(totalPrice(billRequest.getCartRequests()));
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
        Users users = usersService.getUser();
        Bill bill = findById(id).orElseThrow(()->new AppException(ErrorCode.BILL_NOT_FOUND));
        List<String> roleName = users.getRoles().stream().map(Roles::getName).toList();
        if(!(users == bill.getUser()) && !roleName.contains("USER")){

        }
        BillResponse billResponse = mapToResponse(bill);
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy hóa đơn thành công",200,billResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getBillIdUser(BillParam billParam , Pageable pageable){
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
        Product product = productRepository.findById(cartRequest.getProductDetail().getId()).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        BillDetail billDetail = BillDetail.builder()
                .productId(product)
                .quantity(cartRequest.getQuantity())
                .bill(bill)
                .build();
        cartService.delete(cartRequest.getId());
        productService.createNew(product);
        return billDetail;
    }

//    private BigDecimal totalPrice(List<CartRequest> cartRequests){
//        BigDecimal total = BigDecimal.valueOf(0);
//        for (CartRequest cartRequest : cartRequests){
//            Product product = productRepository.findById(cartRequest.getProduct().getId()).get();
//            total = product.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity()));
//        }
//        return total;
//    }

    private BillResponse mapToResponse(Bill bill) {
        BillResponse billResponse = mapper.map(bill, BillResponse.class);

        Users users = usersService.getUser();

        UserResponse userResponse = usersService.validUser(users);
        billResponse.setUserResponse(userResponse);

        List<Product> products = bill.getBillDetail().stream()
                .map(BillDetail::getProductId)
                .toList();

        for (BillDetailResponse billDetailResponse : billResponse.getBillDetail()) {
            Product product = findProductById(products, billDetailResponse.getProductId().getId());
//            if (product != null) {
//                List<String> imageUrl = product.getImages().stream()
//                        .map(image -> UrlBase + image.getName())
//                        .toList();
//                billDetailResponse.getProductId().setImagesUrl(imageUrl);
//            }
        }

        return billResponse;
    }


    private Product findProductById(List<Product> products,Long productId){
        return products.stream().filter(product -> product.getId().equals(productId)).findFirst().orElse(null);
    }

}
