package com.projectRestAPI.MyShop.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.param.ProductParam;
import com.projectRestAPI.MyShop.dto.request.CategoryRequest;
import com.projectRestAPI.MyShop.dto.request.ImageRequest;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.DiscountPeriodRepository;
import com.projectRestAPI.MyShop.repository.DiscountPeriod.ProductDiscountPeriodRepository;
import com.projectRestAPI.MyShop.service.CategoryService;
import com.projectRestAPI.MyShop.service.ImageService;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    private ProductDiscountPeriodRepository productDiscountPeriodRepository;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "category",defaultValue = "")Long category,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit,
                                    @RequestParam(value = "price_gte",defaultValue = "") BigDecimal price_gte,
                                    @RequestParam(value = "price_lte",defaultValue = "") BigDecimal price_lte,
                                    @RequestParam(value = "sort",defaultValue = "") String sort,
                                    @RequestParam(value = "name",defaultValue = "") String name,
                                    @RequestParam(value = "status",defaultValue = "") Integer status,
                                    @RequestParam(value = "idDiscountPeriod",defaultValue = "")Long idDiscountPeriod) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page,limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (idDiscountPeriod != null) {
            List<Long> productsID = productDiscountPeriodRepository.findProductIdsByDiscountId(idDiscountPeriod);

            if (!productsID.isEmpty()) {
                criteriaList.add(new SearchCriteria("id", "notIn", productsID));
            }
        }

//        if (name != null && !name.trim().isEmpty()) {
//            String trimmed = name.replaceAll("\\s+", "").toLowerCase();
//
//            Set<Character> uniqueChars = trimmed.chars()
//                    .mapToObj(c -> (char) c)
//                    .collect(Collectors.toSet());
//
//            for (char c : uniqueChars) {
//                criteriaList.add(new SearchCriteria("name", "charLike", String.valueOf(c)));
//            }
//        }




//        ObjectMapper objectMapper = new ObjectMapper();
//        if(!category.isEmpty()){
//            Category categoryModel = objectMapper.readValue(category,Category.class);
        SearchCriteriaUtils.addCriteria(criteriaList,"category.id" , ":", category);

//        }
        // Trong Controller
//        if (name != null && !name.trim().isEmpty()) {
//            criteriaList.add(new SearchCriteria("id|name", "orLike", name.toLowerCase()));
//        }

        if (name != null && !name.trim().isEmpty()) {
            try {
                Long idValue = Long.parseLong(name);
                criteriaList.add(new SearchCriteria("id", ":", idValue));
//                criteriaList.add(new SearchCriteria("name", "orLike", name));
            } catch (NumberFormatException e) {
                criteriaList.add(new SearchCriteria("name", "like", name));
            }

        }


        SearchCriteriaUtils.addCriteria(criteriaList, "status", ":", status);

        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return productService.getAll(criteriaList,pageable,sortParams);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(@RequestPart("product") @Valid ProductRequest productRequest,
                                 @RequestParam MultiValueMap<String, MultipartFile> productDetails) {
        return productService.addProduct(productRequest,productDetails);
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@RequestPart("product") @Valid ProductRequest productRequest,
                                           @RequestParam MultiValueMap<String, MultipartFile> productDetails){
        return productService.update(productRequest,productDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        return productService.getId(id);
    }

//    @GetMapping("/productDiscount")
//    public ResponseEntity<?> getProductDiscount(){
//        return productService.getId(id);
//    }


    @PutMapping("updateStatus/{id}")
    public ResponseEntity<?> updateStatusProduct(@PathVariable Long id,@RequestBody Integer status){
        return productService.updateStatus(id,status);
    }

    @GetMapping("/getAllProductStatistics")
    public ResponseEntity<?> getAllProductStatistics(){
        return productService.getAllProductStatistics();
    }

}
