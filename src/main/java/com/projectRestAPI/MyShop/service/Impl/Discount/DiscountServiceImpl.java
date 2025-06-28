package com.projectRestAPI.MyShop.service.Impl.Discount;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountMapper;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountShortMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.Discount.DiscountRepository;
import com.projectRestAPI.MyShop.service.DiscountService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl extends BaseServiceImpl<Discount,Long, DiscountRepository> implements DiscountService {
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private DiscountShortMapper discountShortMapper;

    @Autowired
    private UsersService usersService;

    @Override
    public ResponseEntity<ResponseObject> add(DiscountRequest discountRequest) {
        // Kiểm tra trùng mã giảm giá
        if (repository.existsByDiscountCode(discountRequest.getDiscountCode())) {
            throw new AppException(ErrorCode.DUPLICATE_VALUE);
        }

        Discount discount = discountMapper.toDiscount(discountRequest);
        return createNew(discount);
    }


    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<Discount> discount = findById(id); 
        if(discount.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        DiscountResponse discountResponse = discountShortMapper.toDiscountResponse(discount.get());
        return new ResponseEntity<>(new ResponseObject(
                "success",
                "Lấy Thành Công",
                1,
                discountResponse
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(DiscountRequest discountRequest) {
        Optional<Discount> discountOptional = findById(discountRequest.getId());
        if (discountOptional.isEmpty()) {
            throw new AppException(ErrorCode.DISCOUNT_NOT_FOUND);
        }

//        Optional<Discount> existing = repository.findByDiscountCode(discountRequest.getDiscountCode());
//        if (existing.isPresent() && !existing.get().getId().equals(discountRequest.getId())) {
//            throw new AppException(ErrorCode.DUPLICATE_VALUE);
//        }

        Discount discount = discountMapper.toDiscount(discountRequest);
        return createNew(discount);
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatus(DiscountRequest discountRequest) {
        Optional<Discount> discountOptional = findById(discountRequest.getId());
        if (discountOptional.isEmpty()) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        Discount discount = discountOptional.get();
        discount.setStatus(discountRequest.getStatus());
        repository.save(discount);

        return ResponseEntity.ok(ResponseObject.builder()
                .status("success")
                .message("Cập nhật trạng thái thành công")
                .errCode(200)
                .data(discountShortMapper.toDiscountResponse(discount))
                .build());
    }



    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        Page<Discount> response = getAll(params,pageable,sort,null);
        List<Discount> discounts = response.getContent();
        List<DiscountResponse> discountResponses = discountMapper.toListDiscountResponse(discounts);
        ResponseObject responseObject = ResponseObject.builder()
                .status("success")
                .errCode(200)
                .message("Lấy dữ liệu thành công")
                .data(new HashMap<String,Object>(){{
                    put("discounts",discountResponses);
                    put("count",response.getTotalElements());
                }})
                .build();

        return ResponseEntity.ok(responseObject);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllUser() {
        Users users = usersService.getUser();
        return null;
    }
}
