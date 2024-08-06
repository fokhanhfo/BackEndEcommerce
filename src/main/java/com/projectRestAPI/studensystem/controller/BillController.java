package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

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
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id,@RequestBody Integer status){
        return billService.UpdateStatus(id,status);
    }

}
