package com.projectRestAPI.MyShop.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.param.ProductParam;
import com.projectRestAPI.MyShop.dto.request.CategoryRequest;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.service.CategoryService;
import com.projectRestAPI.MyShop.service.ImageService;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "category",defaultValue = "")String category,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit,
                                    @RequestParam(value = "price_gte",defaultValue = "") BigDecimal price_gte,
                                    @RequestParam(value = "price_lte",defaultValue = "") BigDecimal price_lte,
                                    @RequestParam(value = "sort",defaultValue = "") String sort,
                                    @RequestParam(value = "name",defaultValue = "") String name,
                                    @RequestParam(value = "status",defaultValue = "") Integer status) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page,limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if(!category.isEmpty()){
            Category categoryModel = objectMapper.readValue(category,Category.class);
            SearchCriteriaUtils.addCriteria(criteriaList,"category" , ":", categoryModel);
        }
        SearchCriteriaUtils.addCriteria(criteriaList, "name", "like", name);
        SearchCriteriaUtils.addCriteria(criteriaList, "status", ":", status);

        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return productService.getAll(criteriaList,pageable,sortParams);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid ProductRequest productRequest) {
            return productService.addProduct(productRequest);
    }
    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.updateProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        return productService.getId(id);
    }


    @PutMapping("updateStatus/{id}")
    public ResponseEntity<?> updateStatusProduct(@PathVariable Long id,@RequestBody Integer status){
        return productService.updateStatus(id,status);
    }


}
