package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.model.Bill;
import org.springframework.http.ResponseEntity;

public interface BillService extends BaseService<Bill,Long>{
    public ResponseEntity<?> updateStatusBill();
    public ResponseEntity<?> saveBill(BillRequest billRequest);
}
