package com.projectRestAPI.MyShop.controller.Discount;


import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodResponse;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.DiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.DiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.DiscountPeriodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discountPeriod")
public class DiscountPeriodController {
    @Autowired
    private DiscountPeriodService discountPeriodService;

    @Autowired
    private DiscountPeriodRepository discountPeriodRepository;

    @Autowired
    private DiscountPeriodMapper discountPeriodMapper;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountPeriod discountPeriod){
        return discountPeriodService.add(discountPeriod);
    }

    @PutMapping
    private ResponseEntity<ResponseObject> update(@RequestBody @Valid DiscountPeriod discountPeriod){
        return discountPeriodService.update(discountPeriod);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<DiscountPeriod> discountPeriods = discountPeriodRepository.findAll();
        List<DiscountPeriodResponse> discountPeriodResponses = discountPeriodMapper.toListDiscountPeriodResponse(discountPeriods);
        ResponseObject responseObject = ResponseObject.builder()
                .status("success")
                .errCode(200)
                .message("Lấy dữ liệu thành công")
                .data(discountPeriodResponses)
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable("id") Long id){
        return discountPeriodService.getId(id);
    }
}
