package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillService extends BaseService<Bill,Long>{
    ResponseEntity<?> saveBill(BillRequest billRequest);
    ResponseEntity<ResponseObject> getBillId(Long id);

    ResponseEntity<ResponseObject> getBillIdUser();

    ResponseEntity<ResponseObject> UpdateStatus(Long bill_id,Integer status);
//    public ResponseEntity<?> updateStatusBill();
//    public ResponseEntity<?> saveBill(BillRequest billRequest);
//
//    public ResponseEntity<ResponseObject> getAll();
//
//    public  ResponseEntity<ResponseObject> getId(Long id);

}
