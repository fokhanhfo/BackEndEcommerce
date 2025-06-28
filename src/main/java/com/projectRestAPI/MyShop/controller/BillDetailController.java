package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill-detail")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;
    @GetMapping("/top-selling-products")
    public ResponseEntity<ResponseObject> getRevenueByCategory(@RequestParam(required = false) Integer month,
                                                               @RequestParam(required = false) Integer year) {
        return billDetailService.getTop20BestSellingProducts(month, year);
    }
}
