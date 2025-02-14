package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDisCountPeriod;
import com.projectRestAPI.MyShop.service.ProductDiscountPeriodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discountPeriodProduct")
public class ProductDiscountPeriodController {
    @Autowired
    private ProductDiscountPeriodService productDiscountPeriodService;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid ProductDisCountPeriod disCountPeriod){
        return productDiscountPeriodService.add(disCountPeriod);
    }
}
