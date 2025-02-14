package com.projectRestAPI.MyShop.controller.Discount;


import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.service.DiscountPeriodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discountPeriod")
public class DiscountPeriodController {
    @Autowired
    private DiscountPeriodService discountPeriodService;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountPeriod discountPeriod){
        return discountPeriodService.add(discountPeriod);
    }
}
