package com.projectRestAPI.studensystem.controller;


import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.request.ProductRequest;
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
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }

    @PostMapping("/Add")
    public ResponseEntity<?> add(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        Product product = productConvert(productRequest);
        if (product == null) {
            return ResponseEntity.badRequest().body("Category sai");
        }

        return productService.createNew(product);
//        List<Image> images = productRequest.getImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("Fail", "Không tìm thấy id " + id, 1, null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    public Product productConvert(ProductRequest productRequest) {
        Optional<Category> optionalCategory = categoryService.findById(productRequest.getCategory());
        Category category = optionalCategory.orElse(null);
        Product product = Product.builder()
                .name(productRequest.getName())
                .detail(productRequest.getDetail())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .category(category)
                .build();
        List<Image> images = productRequest.getImages();
        imageService.saveImages(images);
        product.setImages(images);
        return product;
    }

    private Image convertToImage(ImageRequest imageRequest) {
        return Image.builder()
                .name_Image(imageRequest.getName_Image())
                .main_photo(imageRequest.getMain_photo())
                .build();
    }

}
