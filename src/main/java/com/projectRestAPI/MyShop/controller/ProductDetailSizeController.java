package com.projectRestAPI.MyShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.service.ProductDetailService;
import com.projectRestAPI.MyShop.service.ProductDetailSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/productDetailSize")
public class ProductDetailSizeController {
    @Autowired
    private ProductDetailSizeService productDetailSizeService;


}
