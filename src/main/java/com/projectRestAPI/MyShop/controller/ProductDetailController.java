package com.projectRestAPI.MyShop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.request.*;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.service.ProductDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<ResponseObject> add(@ModelAttribute @Valid ProductDetailMappingRequest productDetailMappingRequest) throws JsonProcessingException {
        ProductDetailRequest productDetailRequest = productDetailMapper.toProductDetailRequest(productDetailMappingRequest);
        productDetailRequest.setProduct(
                objectMapper.readValue(productDetailMappingRequest.getProduct(), ProductShortRequest.class)
        );
        productDetailRequest.setColor(objectMapper.readValue(
                productDetailMappingRequest.getColor(), ColorRequest.class)
        );
        return productDetailService.add(productDetailRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@ModelAttribute @Valid ProductDetailMappingRequest productDetailMappingRequest) throws JsonProcessingException {
        ProductDetailRequest productDetailRequest = productDetailMapper.toProductDetailRequest(productDetailMappingRequest);
        productDetailRequest.setProduct(
                objectMapper.readValue(productDetailMappingRequest.getProduct(), ProductShortRequest.class)
        );
        productDetailRequest.setColor(objectMapper.readValue(
                productDetailMappingRequest.getColor(), ColorRequest.class)
        );
        return productDetailService.add(productDetailRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getId(@PathVariable Long id ){
        return productDetailService.getId(id);
    }
    }
