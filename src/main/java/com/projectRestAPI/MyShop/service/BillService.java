package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.param.BillParam;
import com.projectRestAPI.MyShop.dto.request.BillRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Bill;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillService extends BaseService<Bill,Long>{

//    ResponseEntity<ResponseObject> getAll(Pageable pageable, BillParam billParam);

    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort);
    ResponseEntity<?> saveBill(BillRequest billRequest);
    ResponseEntity<ResponseObject> getBillId(Long id);

    ResponseEntity<ResponseObject> getBillIdUser(BillParam billParam,Pageable pageable);

    ResponseEntity<ResponseObject> UpdateStatus(Long bill_id,Integer status);

}
