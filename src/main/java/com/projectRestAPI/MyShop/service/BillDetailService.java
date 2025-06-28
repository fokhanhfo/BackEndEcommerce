package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.BestSellingProductDTO;
import com.projectRestAPI.MyShop.dto.TopSellingProductDTO;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.BillDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillDetailService extends BaseService<BillDetail,Long> {
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails);

    public ResponseEntity<ResponseObject> getTop20BestSellingProducts(Integer month, Integer year);
}
