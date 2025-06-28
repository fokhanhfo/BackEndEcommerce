package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.dto.BestSellingProductDTO;
import com.projectRestAPI.MyShop.dto.TopSellingProductDTO;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.BillDetail;
import com.projectRestAPI.MyShop.repository.BillDetailRepository;
import com.projectRestAPI.MyShop.service.BillDetailService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail,Long, BillDetailRepository> implements BillDetailService {
    @Override
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails) {
        return repository.saveAll(billDetails);
    }

    @Override
    public ResponseEntity<ResponseObject> getTop20BestSellingProducts(Integer month, Integer year) {
        Pageable top20 = PageRequest.of(0, 20);
        List<TopSellingProductDTO> topSellingProductDTOS = repository.findTopSellingProductsByMonthAndYear(month, year, top20);
        return ResponseUtil.buildResponse("success","Lấy dữ liệu thành công",1,topSellingProductDTOS, HttpStatus.OK);
    }
}
