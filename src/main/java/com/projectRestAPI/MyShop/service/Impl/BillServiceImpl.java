package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.param.BillParam;
import com.projectRestAPI.MyShop.dto.request.BillRequest;
import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.*;
import com.projectRestAPI.MyShop.mapper.Bill.BillMapper;
import com.projectRestAPI.MyShop.mapper.CartMapper;
import com.projectRestAPI.MyShop.mapper.ImageMapper;
import com.projectRestAPI.MyShop.model.*;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.repository.BillRepository;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailRepository;
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
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private DiscountUserRepository discountUserRepository;

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
        Page<Bill> billPage = getAll(params, pageable, sort, users);
//        List<BillResponse> billResponses = response.getContent().stream().map(this::mapToResponse).toList();
        List<Bill> bills = billPage.getContent();
        List<BillResponse> billResponses = billMapper.toListBillResponse(bills);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("bill",billResponses);
                    put("count",billPage.getTotalElements());
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveBill(BillRequest billRequest) {
        Users user = usersService.getUser();
//        List<Cart> carts = billRequest.getCartRequests().stream()
//                .map(cartRequest -> cartService.findById(cartRequest.getId())
//                        .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND)))
//                .toList();
        List<Cart> carts = cartMapper.toListCart(billRequest.getCartRequests());
//        for (Cart cart : carts){
//            if (cart.getUser() != user && cart.getId() != null){
//                throw new AppException(ErrorCode.UNAUTHORIZED);
//            }
//        }
        Bill bill = mapper.map(billRequest,Bill.class);
        bill.setStatus(0);
//        bill.setTotal_price(totalPrice(billRequest.getCartRequests()));
        bill.setUser(user);
        bill.setCreatedDate(LocalDateTime.now());
        List<BillDetail> billDetails = billRequest.getCartRequests().stream().map(cartRequest -> saveBillDetail(bill,cartRequest)).toList();
        bill.setBillDetail(billDetails);
        Bill bill1 = repository.save(bill);
        List<DiscountUser> discountUsers = billRequest.getDiscountId().stream()
                .map(item -> discountUserRepository.findById(item)
                        .map(discountUser -> {
                            discountUser.setUsed(true);
                            discountUser.setBillId(bill1.getId());
                            // Cập nhật trạng thái Used
                            return discountUser;
                        })
                        .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_USER_NOT_FOUND))
                )
                .toList();
        discountUserRepository.saveAll(discountUsers);
//        BillResponse billResponse = mapToResponse(bill);
        return new ResponseEntity<>(new ResponseObject("Succes","Lập hóa đơn thành công",200,null), HttpStatus.OK);
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
        ProductDetail productDetail = productDetailRepository.findById(cartRequest.getProductDetail().getId()).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        BillDetail billDetail = BillDetail.builder()
                .productDetail(productDetail)
                .quantity(cartRequest.getQuantity())
                .color(cartRequest.getColor().getName())
                .size(cartRequest.getSize().getName())
                .bill(bill)
                .build();
//        if (cartRequest.getId() != null){
//            cartService.delete(cartRequest.getId());
//        }
        Optional.ofNullable(cartRequest.getId())
                .ifPresent(cartService::delete);
        productDetailService.createNew(productDetail);
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
        billResponse.setUser(userResponse);

        List<ProductDetail> productDetails = bill.getBillDetail().stream()
                .map(BillDetail::getProductDetail)
                .toList();

        for (BillDetailResponse billDetailResponse : billResponse.getBillDetail()) {
            ProductDetail productDetail = findProductById(productDetails, billDetailResponse.getProductDetail().getId());
            if (productDetail != null) {
                List<ImageResponse> imageResponses = imageMapper.toListImageResponse(productDetail.getImage());
                billDetailResponse.getProductDetail().setImage(imageResponses);
            }
        }

        return billResponse;
    }


    private ProductDetail findProductById(List<ProductDetail> productDetails,Long productId){
        return productDetails.stream().filter(productDetail -> productDetail.getId().equals(productId)).findFirst().orElse(null);
    }

}
