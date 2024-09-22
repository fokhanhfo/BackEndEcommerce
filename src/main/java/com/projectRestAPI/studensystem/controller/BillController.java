package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.param.BillParam;
import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                 @RequestParam(value = "limit",defaultValue = "12") int limit,
                                                 @RequestParam(value = "start_date", required = false)
                                                     @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                 @RequestParam(value = "end_date", required = false)
                                                     @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                                 @RequestParam(value = "sort",defaultValue = "") String sort,
                                                 @RequestParam(value = "search",defaultValue = "") Integer search,
                                                 @RequestParam(value = "status",defaultValue = "") Integer status){
        Pageable pageable = PageRequest.of(page,limit);
        BillParam billParam = BillParam.builder()
                .endDate(endDate)
                .startDate(startDate)
                .search(search)
                .sort(sort)
                .status(status)
                .build();
        return billService.getAll(pageable,billParam);
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
    public  ResponseEntity<ResponseObject> getUserBill(){
        return billService.getBillIdUser();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id,
                                                       @RequestBody Map<String, Integer> statusMap){
        Integer status = statusMap.get("status");
        return billService.UpdateStatus(id,status);
    }

}
