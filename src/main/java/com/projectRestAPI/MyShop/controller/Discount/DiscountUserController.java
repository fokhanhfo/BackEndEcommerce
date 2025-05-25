package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserAllRequest;
import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.DiscountService;
import com.projectRestAPI.MyShop.service.DiscountUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discountUser")
public class DiscountUserController {
    @Autowired
    private DiscountUserService discountUserService;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountUserRequest discountUserRequest){
        return discountUserService.add(discountUserRequest);
    }

    @PostMapping("/addAll")
    private ResponseEntity<ResponseObject> addAll(@RequestBody @Valid DiscountUserAllRequest discountUserRequest){
        return discountUserService.addAll(discountUserRequest);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id){
        return discountUserService.deleteDiscount(id);
    }

    @GetMapping("/user")
    private ResponseEntity<ResponseObject> getDiscountsByUserId(@RequestParam(value = "isUsed", required = false) Boolean isUsed) {
        return discountUserService.getAllDiscountByUser(isUsed);
    }

}
