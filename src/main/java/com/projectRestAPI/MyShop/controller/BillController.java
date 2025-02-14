package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.param.BillParam;
import com.projectRestAPI.MyShop.dto.request.BillRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.BillService;
import com.projectRestAPI.MyShop.service.SearchCriteriaService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private SearchCriteriaService searchCriteriaService;

//    @GetMapping
//    public ResponseEntity<ResponseObject> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
//                                                 @RequestParam(value = "limit",defaultValue = "12") int limit,
//                                                 @RequestParam(value = "start_date", required = false)
//                                                     @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime startDate,
//                                                 @RequestParam(value = "end_date", required = false)
//                                                     @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime endDate,
//                                                 @RequestParam(value = "sort",defaultValue = "") String sort,
//                                                 @RequestParam(value = "search",defaultValue = "") Long search,
//                                                 @RequestParam(value = "status",defaultValue = "") Integer status){
//        Sort sortOption = Sort.by(Sort.Order.desc("id"));
//        if (!sort.isEmpty()) {
//            String[] sortParts = sort.split(":");
//            if (sortParts.length == 2) {
//                String attribute = sortParts[0];
//                String direction = sortParts[1].toUpperCase();
//                if ("ASC".equals(direction)) {
//                    sortOption = Sort.by(Sort.Order.asc(attribute));
//                } else if ("DESC".equals(direction)) {
//                    sortOption = Sort.by(Sort.Order.desc(attribute));
//                }
//            }
//        }
//        Pageable pageable = PageRequest.of(page,limit,sortOption);
//        BillParam billParam = BillParam.builder()
//                .endDate(endDate)
//                .startDate(startDate)
//                .search(search)
//                .sort(sort)
//                .status(status)
//                .build();
//        return billService.getAll(pageable,billParam);
//    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                     @RequestParam(value = "limit",defaultValue = "12") int limit,
                                                     @RequestParam(value = "start_date", required = false)
                                                         @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime startDate,
                                                     @RequestParam(value = "end_date", required = false)
                                                         @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime endDate,
                                                     @RequestParam(value = "sort",required = false) String sort,
                                                     @RequestParam(value = "search",required = false) Long search,
                                                     @RequestParam(value = "status",required = false) Integer status ) {

        // Tạo Pageable
        Pageable pageable = PageRequest.of(page, limit);

        List<SearchCriteria> criteriaList = new ArrayList<>();
        SearchCriteriaUtils.addCriteria(criteriaList,"createdDate" , ">", startDate);
        SearchCriteriaUtils.addCriteria(criteriaList, "createdDate", "<", endDate);
        SearchCriteriaUtils.addCriteria(criteriaList, "id", "like", search);
        SearchCriteriaUtils.addCriteria(criteriaList, "status", ":", status);

        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }

        // Gọi service để lấy dữ liệu
        return billService.getAll(criteriaList, pageable, sortParams);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody BillRequest billRequest){
        return billService.saveBill(billRequest);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ResponseObject> getIdBill(@PathVariable Long id){
        return billService.getBillId(id);
    }

    @GetMapping("/user")
    public  ResponseEntity<ResponseObject> getUserBill(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                       @RequestParam(value = "limit",defaultValue = "12") int limit,
                                                       @RequestParam(value = "search",defaultValue = "") Long search,
                                                       @RequestParam(value = "status",defaultValue = "") Integer status){
        Pageable pageable = PageRequest.of(page,limit);
        BillParam billParam = BillParam.builder()
                .search(search)
                .status(status)
                .build();
        return billService.getBillIdUser(billParam,pageable);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id,
                                                       @RequestBody Map<String, Integer> statusMap){
        Integer status = statusMap.get("status");
        return billService.UpdateStatus(id,status);
    }

}
