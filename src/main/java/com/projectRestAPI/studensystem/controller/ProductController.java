package com.projectRestAPI.studensystem.controller;


import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.request.ProductRequest;
import com.projectRestAPI.studensystem.dto.request.UpdateStatusProductRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Category;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.CategoryRepository;
import com.projectRestAPI.studensystem.repository.ImageRepository;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.CategoryService;
import com.projectRestAPI.studensystem.service.ImageService;
import com.projectRestAPI.studensystem.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(){
        return productService.getAll();
    }


    @PostMapping("/Add")
    public ResponseEntity<?> add(@Valid @ModelAttribute ProductRequest productRequest){
        return productService.addProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        return productService.getId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest);
    }

    @PutMapping("updateStatus/{id}")
    public ResponseEntity<?> updateStatusProduct(@PathVariable Long id,@RequestBody Integer status){
        return productService.updateStatus(id,status);
    }


}
