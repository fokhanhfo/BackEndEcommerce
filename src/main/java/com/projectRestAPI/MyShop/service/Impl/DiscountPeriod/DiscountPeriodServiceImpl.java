package com.projectRestAPI.MyShop.service.Impl.DiscountPeriod;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountPeriodRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.DiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.DiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.DiscountPeriodService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountPeriodServiceImpl extends BaseServiceImpl<DiscountPeriod,Long, DiscountPeriodRepository> implements DiscountPeriodService {

    @Autowired
    private DiscountPeriodRepository discountPeriodRepository;
    @Autowired
    private DiscountPeriodMapper discountPeriodMapper;
    @Override
    public ResponseEntity<ResponseObject> add(DiscountPeriod discountPeriodRequest) {
        Boolean existsByDiscountPeriodCode = discountPeriodRepository.existsByDiscountPeriodCode(discountPeriodRequest.getDiscountPeriodCode());
        if(existsByDiscountPeriodCode){
            throw new AppException(ErrorCode.DUPLICATE_DISCOUNT_CODE);
        }
        DiscountPeriod discountPeriodSave = discountPeriodRepository.save(discountPeriodRequest);
        return ResponseUtil.buildResponse(
                "success",
                "Thêm dữ liệu thành công",
                1,
                null,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<DiscountPeriod> discount = discountPeriodRepository.findById(id);
        if(discount.isEmpty()){
            throw new AppException(ErrorCode.DISCOUNT_NOT_FOUND);
        }
        DiscountPeriodResponse discountPeriod = discountPeriodMapper.toDiscountPeriodResponse(discount.get());
        return new ResponseEntity<>(new ResponseObject(
                "success",
                "Lấy Thành Công",
                1,
                discountPeriod
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(DiscountPeriod discountPeriod) {
        Optional<DiscountPeriod> discountOptional = discountPeriodRepository.findById(discountPeriod.getId());
        if(discountOptional.isEmpty()){
            throw new AppException(ErrorCode.DISCOUNT_PERIOD_NOT_FOUND);
        }
        if (discountPeriodRepository.existsByDiscountPeriodCodeAndIdNot(discountPeriod.getDiscountPeriodCode(), discountPeriod.getId())) {
            throw new AppException(ErrorCode.DUPLICATE_DISCOUNT_CODE);
        }
        discountPeriodRepository.save(discountPeriod);
        return ResponseUtil.buildResponse("seccess","Cập nhật dữ liệu thành công",1,null,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteDiscountPeriod(Long id){
        Optional<DiscountPeriod> otp = discountPeriodRepository.findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        discountPeriodRepository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, null), HttpStatus.OK);
    }


    @Override
    public void setRepository(DiscountPeriodRepository repository) {
        super.setRepository(repository);
    }
}
