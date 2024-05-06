package com.projectRestAPI.studensystem.controller.ControllerClient;

import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@CrossOrigin("*")
public class ProductClientController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "category",defaultValue = "")Long category_id){
        Pageable pageable = PageRequest.of(page,2);
        List<Product> products = productService.getAllProduct(pageable,category_id).getContent();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
