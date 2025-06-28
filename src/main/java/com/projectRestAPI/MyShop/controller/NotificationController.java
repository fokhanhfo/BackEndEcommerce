package com.projectRestAPI.MyShop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.service.NotificationService;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "sort",defaultValue = "") String sort,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page,limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();
        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return notificationService.getAll(criteriaList,pageable, sortParams);
    }
}
