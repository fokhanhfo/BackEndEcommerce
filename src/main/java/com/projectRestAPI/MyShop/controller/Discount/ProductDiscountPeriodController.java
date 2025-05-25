package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserAllRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.ProductDiscountPeriodAllRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import com.projectRestAPI.MyShop.service.ProductDiscountPeriodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discountProductPeriod")
public class ProductDiscountPeriodController {
    @Autowired
    private ProductDiscountPeriodService productDiscountPeriodService;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid ProductDiscountPeriod disCountPeriod){
        return productDiscountPeriodService.add(disCountPeriod);
    }

    @PostMapping("/addAll")
    private ResponseEntity<ResponseObject> addAll(@RequestBody @Valid ProductDiscountPeriodAllRequest productDiscountPeriodAllRequest){
        return productDiscountPeriodService.addAll(productDiscountPeriodAllRequest);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        return productDiscountPeriodService.deleteDiscountPeriod(id);
    }
}
