package com.projectRestAPI.MyShop.service.Impl.DiscountPeriod;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.ProductDiscountPeriodAllRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.DiscountStatus;
import com.projectRestAPI.MyShop.mapper.ProductMapper;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.ProductDiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductDiscountPeriodService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDiscountPeriodServiceImpl extends BaseServiceImpl<ProductDiscountPeriod,Long, ProductDiscountPeriodRepository> implements ProductDiscountPeriodService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseEntity<ResponseObject> add(ProductDiscountPeriod discountPeriod) {
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> addAll(ProductDiscountPeriodAllRequest productDiscountPeriodAllRequest) {
        List<ProductDiscountPeriod> productDiscountPeriods = productDiscountPeriodAllRequest.getPercentageValue().stream()
                .map(item -> ProductDiscountPeriod.builder()
                        .product(productMapper.toProduct(item.getProduct()))
                        .percentageValue(item.getPercentageValue())
                        .discountPeriod(productDiscountPeriodAllRequest.getDiscountPeriod())
                        .build())
                .collect(Collectors.toList());

        repository.saveAll(productDiscountPeriods);
        return ResponseUtil.buildResponse("success", "Thêm thành công", 1, null, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<ProductDiscountPeriod> discount = findById(id);
        if(discount.isEmpty()){
            throw new AppException(ErrorCode.DISCOUNT_NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseObject(
                "success",
                "Lấy Thành Công",
                1,
                discount.get()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(ProductDiscountPeriod discountPeriod) {
        Optional<ProductDiscountPeriod> discountOptional = findById(discountPeriod.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        return createNew(discountPeriod);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteDiscountPeriod(Long id) {
        Optional<ProductDiscountPeriod> otp = findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, null), HttpStatus.OK);
    }


}
