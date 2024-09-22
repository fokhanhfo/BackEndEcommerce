package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.param.BillParam;
import com.projectRestAPI.studensystem.dto.request.BillRequest;
import com.projectRestAPI.studensystem.dto.request.CartRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Bill;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillService extends BaseService<Bill,Long>{

    ResponseEntity<ResponseObject> getAll(Pageable pageable, BillParam billParam);
    ResponseEntity<?> saveBill(BillRequest billRequest);
    ResponseEntity<ResponseObject> getBillId(Long id);

    ResponseEntity<ResponseObject> getBillIdUser();

    ResponseEntity<ResponseObject> UpdateStatus(Long bill_id,Integer status);

}
