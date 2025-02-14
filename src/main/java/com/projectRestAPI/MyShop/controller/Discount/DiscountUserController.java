package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.DiscountService;
import com.projectRestAPI.MyShop.service.DiscountUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discountUser")
public class DiscountUserController {
    @Autowired
    private DiscountUserService discountUserService;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountUserRequest discountUserRequest){
        return discountUserService.add(discountUserRequest);
    }
}
