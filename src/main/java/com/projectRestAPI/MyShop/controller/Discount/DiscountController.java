package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.repository.Discount.DiscountRepository;
import com.projectRestAPI.MyShop.service.DiscountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.TypeToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountRequest discountRequest){
        return discountService.add(discountRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "validity", required = false) String validity,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sort",defaultValue = "") String sort,
            @RequestParam(value = "search",defaultValue = "") String search
    ) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (type != null) {
            criteriaList.add(new SearchCriteria("type", ":", type));
        }

        if (category != null) {
            criteriaList.add(new SearchCriteria("category", ":", category));
        }

        if (startTime != null) {
            criteriaList.add(new SearchCriteria("startTime", ">=", startTime));
        }

        if (endTime != null) {
            criteriaList.add(new SearchCriteria("endTime", "<=", endTime));
        }

        if (validity != null && !validity.isBlank()) {
            LocalDateTime now = LocalDateTime.now();

            switch (validity) {
                case "ongoing":
                    criteriaList.add(new SearchCriteria("startTime", "<=", now));
                    criteriaList.add(new SearchCriteria("endTime", ">=", now));
                    break;
                case "upcoming":
                    criteriaList.add(new SearchCriteria("startTime", ">", now));
                    break;
                case "ended":
                    criteriaList.add(new SearchCriteria("endTime", "<", now));
                    break;
            }
        }
        if(search != null){
            try {
                Long idValue = Long.parseLong(search);
                criteriaList.add(new SearchCriteria("id", ":", idValue));
//                criteriaList.add(new SearchCriteria("name", "orLike", name));
            } catch (NumberFormatException e) {
                criteriaList.add(new SearchCriteria("discountCode", "like", search));
            }
        }

        Pageable pageable = PageRequest.of(page, limit);
        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return discountService.getAll(criteriaList,pageable,sortParams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable("id") Long id){
        return discountService.getId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return discountService.deleteDiscount(id);
    }

    @PutMapping
    public ResponseEntity<ResponseObject> update(@RequestBody DiscountRequest discountRequest) {
        return discountService.update(discountRequest);
    }

    @PutMapping("/update-status")
    public ResponseEntity<ResponseObject> updateStatus(@RequestBody DiscountRequest discountRequest) {
        return discountService.updateStatus(discountRequest);
    }

}
