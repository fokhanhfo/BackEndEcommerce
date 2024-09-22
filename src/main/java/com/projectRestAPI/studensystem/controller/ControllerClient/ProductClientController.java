package com.projectRestAPI.studensystem.controller.ControllerClient;

import com.projectRestAPI.studensystem.dto.param.ProductParam;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.enums.StatusProduct;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ProductClientController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "category",defaultValue = "")Long category_id,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit,
                                    @RequestParam(value = "price_gte",defaultValue = "") BigDecimal price_gte,
                                    @RequestParam(value = "price_lte",defaultValue = "") BigDecimal price_lte,
                                    @RequestParam(value = "sort",defaultValue = "") String sort,
                                    @RequestParam(value = "search",defaultValue = "") String search,
                                    @RequestParam(value = "status",defaultValue = "") Integer status){
        Pageable pageable = PageRequest.of(page,limit);
        ProductParam productParam = ProductParam.builder()
                .categoryId(category_id)
                .priceGte(price_gte)
                .priceLte(price_lte)
                .status(status)
                .sort(sort)
                .search(search)
                .build();
        return productService.getAllProduct(pageable,productParam);
    }

    @GetMapping("/newProduct")
    public  ResponseEntity<ResponseObject> getNewProduct(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                         @RequestParam(value = "limit",defaultValue = "12") int limit){
        Integer maxPage = 2;
        if(page>maxPage){
            page=maxPage;
        }
        Pageable pageable = PageRequest.of(page,limit);
        return productService.getNewProduct(pageable);
    }

}
