package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.controller.NotificationController;
import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyOrderCount;
import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyRevenue;
import com.projectRestAPI.MyShop.dto.BillStatistics.OrderStatusRatio;
import com.projectRestAPI.MyShop.dto.BillStatistics.PaymentMethodRevenue;
import com.projectRestAPI.MyShop.dto.param.BillParam;
import com.projectRestAPI.MyShop.dto.request.BillRequest;
import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.*;
import com.projectRestAPI.MyShop.enums.DiscountCategory;
import com.projectRestAPI.MyShop.enums.DiscountStatus;
import com.projectRestAPI.MyShop.enums.DiscountType;
import com.projectRestAPI.MyShop.enums.StatusBill;
import com.projectRestAPI.MyShop.mapper.Bill.BillMapper;
import com.projectRestAPI.MyShop.mapper.Cart.CartMapper;
import com.projectRestAPI.MyShop.mapper.ImageMapper;
import com.projectRestAPI.MyShop.model.*;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import com.projectRestAPI.MyShop.model.Notification.Notification;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;
import com.projectRestAPI.MyShop.repository.*;
import com.projectRestAPI.MyShop.repository.Discount.DiscountRepository;
import com.projectRestAPI.MyShop.repository.Discount.DiscountUserRepository;
import com.projectRestAPI.MyShop.service.*;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import com.projectRestAPI.MyShop.utils.StatusBillUltil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductDetailSizeRepository productDetailSizeRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private NotificationController notificationController;

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

        OtpVerification otpEntity = otpVerificationRepository
                .findFirstByEmailOrderByExpirationTimeDesc(user.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.OTP_NOT_FOUND));

        if (!otpEntity.getOtp().equals(billRequest.getOtp())) {
            throw new AppException(ErrorCode.OTP_IS_INCORRECT);
        }

        if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_HAS_EXPIRED);
        }

        List<Cart> carts = cartMapper.toListCart(billRequest.getCartRequests());

        Bill bill = mapper.map(billRequest, Bill.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            if (roles.size() == 1 && roles.contains("ROLE_USER")) {
                bill.setStatus(0);
            } else {
                bill.setStatus(5);
            }
        }


        bill.setUser(user);
        bill.setCreatedDate(LocalDateTime.now());
        AtomicReference<BigDecimal> totalPriceRef = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> discountShipRef = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> discountUserRef = new AtomicReference<>(BigDecimal.ZERO);
        List<BillDetail> billDetails = billRequest.getCartRequests().stream()
                .map(cartRequest -> {
                    Optional<Product> productOptional = productRepository.findById(cartRequest.getProductDetail().getProduct().getId());
                    if(productOptional.isEmpty()){
                        throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
                    }
                    Product product = productOptional.get();
                    BigDecimal sellingPrice = product.getSellingPrice();
                    List<ProductDiscountPeriod> productDiscountPeriods = product.getProductDiscountPeriods().stream().filter((productDiscountPeriod)->{
                        LocalDateTime now = LocalDateTime.now();
                       LocalDateTime startTime = productDiscountPeriod.getDiscountPeriod().getStartTime();
                       LocalDateTime endTime = productDiscountPeriod.getDiscountPeriod().getEndTime();
                       Integer status = productDiscountPeriod.getDiscountPeriod().getStatus();
                        return (startTime.isBefore(now) || startTime.isEqual(now))
                                && (endTime.isAfter(now) || endTime.isEqual(now))
                                && status == DiscountStatus.ACTIVE.ordinal();
                    }).toList();
                    ProductDiscountPeriod productDiscountPeriod = productDiscountPeriods
                            .stream()
                            .reduce((max, current) -> {
                                int currentValue = current.getPercentageValue() != null ? current.getPercentageValue() : 0;
                                int maxValue = max.getPercentageValue() != null ? max.getPercentageValue() : 0;
                                return currentValue > maxValue ? current : max;
                            })
                            .orElse(null);
                    BigDecimal itemTotal = getBigDecimal(cartRequest, productDiscountPeriod, sellingPrice);
                    totalPriceRef.updateAndGet(current -> current.add(itemTotal));
                    return saveBillDetail(bill, cartRequest);
                })
                .toList();

        bill.setBillDetail(billDetails);
        bill.setPayMethod(bill.getPayMethod());

        if (billRequest.getDiscountUser() != null) {
            bill.setDiscountUser(billRequest.getDiscountUser());
        }
        if (billRequest.getDiscountShip() != null) {
            bill.setDiscountShip(billRequest.getDiscountShip());
        }

        if (billRequest.getDiscountId() != null && !billRequest.getDiscountId().isEmpty()) {

            List<DiscountUser> discountUsers = billRequest.getDiscountId().stream()
                    .map(discountId -> {
                        Discount discount = discountRepository.findById(discountId)
                                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
                        BigDecimal total_price = totalPriceRef.get();
                        if (discount != null) {
                            if (discount.getType() == 1 && Objects.equals(discount.getCategory(), DiscountCategory.VAN_CHUYEN.getDiscountCategory())) {
                                // Giảm theo phần trăm
                                BigDecimal percentValue = BigDecimal.valueOf(30000)
                                        .multiply(discount.getValue().divide(BigDecimal.valueOf(100)));
                                BigDecimal shipDiscount = percentValue.min(discount.getMaxValue());
                                discountShipRef.set(discountShipRef.get().add(shipDiscount));
                            } else if(Objects.equals(discount.getCategory(), DiscountCategory.VAN_CHUYEN.getDiscountCategory())) {
                                // Giảm theo giá trị cố định
                                discountShipRef.set(discountShipRef.get().add(discount.getValue()));
                            }

                            // Tính giảm giá cho người dùng nếu đủ điều kiện
                            if (total_price.compareTo(discount.getDiscountCondition()) >= 0 && Objects.equals(discount.getCategory(), DiscountCategory.SAN_PHAM.getDiscountCategory())) {
                                if (discount.getType() == 1) {
                                    BigDecimal percentValue = total_price
                                            .multiply(discount.getValue().divide(BigDecimal.valueOf(100)));
                                    BigDecimal userDiscount = percentValue.min(discount.getMaxValue());
                                    discountUserRef.set(discountUserRef.get().add(userDiscount));
                                } else {
                                    discountUserRef.set(discountUserRef.get().add(discount.getValue()));
                                }
                            }

                        }

                        DiscountUser discountUser = discountUserRepository.findByDiscountAndUsers(discount, user);
                        if (discountUser == null) {
                            throw new AppException(ErrorCode.DISCOUNT_USER_NOT_FOUND);
                        }
                        discountUser.setUsed(true);
                        discountUser.setBillId(bill.getId());
                        return discountUser;
                    })
                    .toList();
            discountUserRepository.saveAll(discountUsers);
        }

        bill.setTotal_price(totalPriceRef.get());
        bill.setDiscountUser(discountUserRef.get());
        bill.setDiscountShip(discountShipRef.get());
        Bill bill1 = repository.save(bill);
        otpVerificationRepository.delete(otpEntity);
        Notification noti = Notification.builder()
                .title("Đơn hàng mới")
                .content("Khách hàng " + user.getUsername() + " đã đặt mua: " + ". Đơn hàng mã " + bill1.getId())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .recipient("admin")
                .build();

        notificationRepository.save(noti);
        messagingTemplate.convertAndSend("/topic/orders", noti);

        return new ResponseEntity<>(new ResponseObject("Succes", "Lập hóa đơn thành công", 200, null), HttpStatus.OK);
    }


        public static BigDecimal calculateVoucherProductDiscount(Discount selectedVoucherProduct, BigDecimal totalPrice) {
            if (selectedVoucherProduct != null && totalPrice.compareTo(selectedVoucherProduct.getDiscountCondition()) >= 0) {
                if (selectedVoucherProduct.getType() == 1) { // Phần trăm
                    BigDecimal percentDiscount = totalPrice.multiply(selectedVoucherProduct.getValue().divide(BigDecimal.valueOf(100)));
                    return percentDiscount.compareTo(selectedVoucherProduct.getMaxValue()) < 0
                            ? percentDiscount
                            : selectedVoucherProduct.getMaxValue();
                } else {
                    return selectedVoucherProduct.getValue(); // Tiền mặt
                }
            }
            return BigDecimal.ZERO;
        }

        public static BigDecimal calculateFreeShipDiscount(Discount selectedFreeShipVoucher) {
            BigDecimal shipFee = BigDecimal.valueOf(30000); // Cố định phí ship 30k
            if (selectedFreeShipVoucher != null) {
                if (selectedFreeShipVoucher.getType() == 1) { // Phần trăm
                    BigDecimal percentDiscount = shipFee.multiply(selectedFreeShipVoucher.getValue().divide(BigDecimal.valueOf(100)));
                    return percentDiscount.compareTo(selectedFreeShipVoucher.getMaxValue()) < 0
                            ? percentDiscount
                            : selectedFreeShipVoucher.getMaxValue();
                } else {
                    return selectedFreeShipVoucher.getValue(); // Tiền mặt
                }
            }
            return BigDecimal.ZERO;
        }

    private static BigDecimal getBigDecimal(CartRequest cartRequest, ProductDiscountPeriod productDiscountPeriod, BigDecimal sellingPrice) {
        int percentageValue = productDiscountPeriod != null && productDiscountPeriod.getPercentageValue() != null
                ? productDiscountPeriod.getPercentageValue()
                : 0;

        BigDecimal discountMultiplier = BigDecimal.valueOf(100 - percentageValue)
                .divide(BigDecimal.valueOf(100));
        BigDecimal finalPrice = sellingPrice.multiply(discountMultiplier);

        // Tính tổng giá cho từng sản phẩm
        return finalPrice.multiply(BigDecimal.valueOf(cartRequest.getQuantity()));
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
    public ResponseEntity<ResponseObject> UpdateStatus(Long bill_id, Integer status, String note) {
        Bill bill = findById(bill_id)
                .orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));

        // Lấy role của người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        boolean isAdmin = roles.contains("ROLE_ADMIN");
        boolean isStaff = roles.contains("ROLE_STAFF");
        boolean isUser = roles.contains("ROLE_USER");
        boolean isCancelRequest = Objects.equals(status, StatusBill.CANCELLED.getStatus());

        // phân quyền:
        if (isAdmin || isStaff) {
            // quyền cao nhất, toàn quyền update
        } else if (isUser) {
            // user chỉ được phép huỷ đơn
            if (!isCancelRequest) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } else {
            // role không hợp lệ
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Nếu là yêu cầu huỷ, trả lại hàng tồn kho
        if (isCancelRequest) {
            List<BillDetail> billDetails = bill.getBillDetail();
            List<ProductDetailSize> productDetailSizes = new ArrayList<>();

            for (BillDetail item : billDetails) {
                ProductDetail productDetail = item.getProductDetail();
                ProductDetailSize productDetailSize = productDetail.getProductDetailSizes().stream()
                        .filter(pds -> pds.getSize().getName().equals(item.getSize()))
                        .findFirst()
                        .orElse(null);

                if (productDetailSize != null) {
                    productDetailSize.setQuantity(productDetailSize.getQuantity() + item.getQuantity());
                    productDetailSizes.add(productDetailSize);
                }
            }

            productDetailSizeRepository.saveAll(productDetailSizes);
            bill.setNote(note);
        }

        // cập nhật trạng thái
        bill.setStatus(status);
        createNew(bill);

        BillResponse billResponse = mapToResponse(bill);
        Notification notification = Notification.builder()
                .title("Cập nhật trạng thái đơn hàng")
                .content("Đơn hàng #" + bill.getId() + " đã chuyển trạng thái sang " + StatusBillUltil.getStatusDescription(status))
                .createdAt(LocalDateTime.now())
                .recipient(bill.getUser().getUsername()) // giả sử Bill có getUser()
                .build();
        notificationRepository.save(notification);
        notificationController.sendNotification(notification.getRecipient(), notification);
        return new ResponseEntity<>(
                new ResponseObject("Success", "Cập nhật trạng thái hóa đơn thành công", 200, billResponse),
                HttpStatus.OK
        );
    }



    private BillDetail saveBillDetail(Bill bill, CartRequest cartRequest) {
        ProductDetail productDetail = productDetailRepository.findById(
                cartRequest.getProductDetail().getId()
        ).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));

        Product product = productRepository.findById(
                cartRequest.getProductDetail().getProduct().getId()
        ).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        BigDecimal sellingPrice = product.getSellingPrice();
        ProductDiscountPeriod discount = product.getProductDiscountPeriods().stream()
                .filter((productDiscountPeriod) -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime startTime = productDiscountPeriod.getDiscountPeriod().getStartTime();
                    LocalDateTime endTime = productDiscountPeriod.getDiscountPeriod().getEndTime();
                    Integer status = productDiscountPeriod.getDiscountPeriod().getStatus();
                    return (startTime.isBefore(now) || startTime.isEqual(now))
                            && (endTime.isAfter(now) || endTime.isEqual(now))
                            && status == DiscountStatus.ACTIVE.ordinal();
                })
                .reduce((max, current) -> current.getPercentageValue() > max.getPercentageValue() ? current : max)
                .orElse(null);
        BigDecimal finalPrice;
        if (discount != null && discount.getPercentageValue() != null) {
            BigDecimal percentage = BigDecimal.valueOf(discount.getPercentageValue()).divide(BigDecimal.valueOf(100));
            finalPrice = sellingPrice.multiply(BigDecimal.ONE.subtract(percentage));
        } else {
            finalPrice = sellingPrice;
        }




        // Tìm ProductDetailSize dựa vào productDetail và size
        ProductDetailSize productDetailSize = productDetailSizeRepository
                .findByProductDetailIdAndSizeId(
                        productDetail.getId(),
                        cartRequest.getSize().getId()
                )
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        // Kiểm tra quantity có đủ không
        if (productDetailSize.getQuantity() < cartRequest.getQuantity()) {
            throw new AppException(ErrorCode.INSUFFICIENT_QUANTITY);
        }

        // Trừ số lượng
        productDetailSize.setQuantity(
                productDetailSize.getQuantity() - cartRequest.getQuantity()
        );

        // Lưu lại ProductDetailSize
        productDetailSizeRepository.save(productDetailSize);

        BillDetail billDetail = BillDetail.builder()
                .productDetail(productDetail)
                .quantity(cartRequest.getQuantity())
                .color(cartRequest.getColor().getName())
                .size(cartRequest.getSize().getName())
                .bill(bill)
                .sellingPrice(product.getSellingPrice())
                .discountPrice(finalPrice)
                .build();

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

    @Override
    public ResponseEntity<ResponseObject> billStatistics() {
        List<MonthlyRevenue> monthlyRevenue = repository.getMonthlyRevenueCurrentYear();
        List<MonthlyOrderCount> monthlyOrderCount = repository.getMonthlyOrderCount();
        List<PaymentMethodRevenue> revenueByPaymentMethod = repository.getRevenueByPaymentMethod();
        List<OrderStatusRatio> orderStatusRatio = repository.getOrderStatusRatio();
        return ResponseUtil.buildResponse("success","Lấy dữ liệu thành công",1,new BillStatisticsResponse(
                monthlyRevenue,
                monthlyOrderCount,
                revenueByPaymentMethod,
                orderStatusRatio
        ),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getMonthlyStatistics(int year) {
        List<Bill> bills = repository.findAllCompletedByYear(year);

        Map<Integer, List<Bill>> groupedByMonth = bills.stream()
                .collect(Collectors.groupingBy(b -> b.getCreatedDate().getMonthValue()));

        List<MonthlyStatisticDTO> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            List<Bill> monthlyBills = groupedByMonth.getOrDefault(month, new ArrayList<>());

            BigDecimal revenue = BigDecimal.ZERO;
            Set<Long> customerIds = new HashSet<>();

            for (Bill bill : monthlyBills) {
                BigDecimal totalPrice = bill.getTotal_price() != null ? bill.getTotal_price() : BigDecimal.ZERO;
                BigDecimal discountUser = bill.getDiscountUser() != null ? bill.getDiscountUser() : BigDecimal.ZERO;
                BigDecimal discountShip = bill.getDiscountShip() != null ? bill.getDiscountShip() : BigDecimal.ZERO;
                BigDecimal defaultShip = new BigDecimal("30000");

                BigDecimal thisRevenue = totalPrice
                        .subtract(discountUser)
                        .add(defaultShip.subtract(discountShip));

                revenue = revenue.add(thisRevenue);

                if (bill.getUser() != null && bill.getUser().getId() != null) {
                    customerIds.add(bill.getUser().getId());
                }
            }

            MonthlyStatisticDTO dto = new MonthlyStatisticDTO(
                    month,
                    revenue,
                    monthlyBills.size(),
                    customerIds.size()
            );

            result.add(dto);
        }
        return ResponseUtil.buildResponse("success","Lấy dữ liệu thành công",1,result,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResponseObject> getRevenueByCategory(int year) {
        List<RevenueByCategoryDTO> revenue = repository.getRevenueByCategoryAndYear(year);
        return ResponseUtil.buildResponse("success","Lấy dữ liệu thành công",1,revenue,HttpStatus.OK);
    }
}
