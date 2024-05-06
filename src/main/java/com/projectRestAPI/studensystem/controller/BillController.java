package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.response.BillDetailResponse;
import com.projectRestAPI.studensystem.dto.response.BillResponse;
import com.projectRestAPI.studensystem.dto.response.CartResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Bill;
import com.projectRestAPI.studensystem.model.BillDetail;
import com.projectRestAPI.studensystem.model.Cart;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.BillDetailService;
import com.projectRestAPI.studensystem.service.BillService;
import com.projectRestAPI.studensystem.service.CartService;
import com.projectRestAPI.studensystem.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BillRequest billRequest){
        return billService.saveBill(billRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Bill> bills= billService.findAll();
        List<BillResponse> billResponses = new ArrayList<>();
        for (Bill bill : bills){
            BillResponse billResponse =mapper.map(bill,BillResponse.class);
            billResponse.setName_user(bill.getUser().getFullName());
            billResponses.add(billResponse);
        }
        return new ResponseEntity<>(billResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        Optional<Bill> billOpt = billService.findById(id);
        if(billOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("error","Không thấy id",0,null),HttpStatus.BAD_REQUEST);
        }
        Bill bill= billOpt.get();
        BillResponse billResponse = mapper.map(bill,BillResponse.class);
        billResponse.setName_user(bill.getUser().getFullName());
        return new ResponseEntity<>(new ResponseObject("succes","OK",1,billResponse),HttpStatus.OK);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<?> getBillDetail(@PathVariable Long id){
        Optional<Bill> billOpt = billService.findById(id);
        if(billOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseObject("error","Không thấy id",0,null),HttpStatus.BAD_REQUEST);
        }
        Bill bill= billOpt.get();
        BillDetailResponse billDetailResponse = mapper.map(bill,BillDetailResponse.class);
        billDetailResponse.setName_user(bill.getUser().getFullName());
        billDetailResponse.setBillDetails(bill.getBillDetail());
        return new ResponseEntity<>(new ResponseObject("succes","OK",1,billDetailResponse),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id){
        return null;
    }
}
