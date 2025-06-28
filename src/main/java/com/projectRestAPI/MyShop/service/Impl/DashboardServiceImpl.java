package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.dto.Dashboard.BillStatusCountDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.ProductInventoryDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.QuantityByYearResponse;
import com.projectRestAPI.MyShop.dto.Dashboard.RevenueResponse;
import com.projectRestAPI.MyShop.dto.ProductStatistics.ProductQuantityDTO;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.repository.BillDetailRepository;
import com.projectRestAPI.MyShop.repository.BillRepository;
import com.projectRestAPI.MyShop.repository.ProductRepository;
import com.projectRestAPI.MyShop.service.DashboardService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ResponseEntity<ResponseObject> getStatCardDashboard() {
        List<RevenueResponse> revenueResponses = billRepository.getRevenuePerYear();
        List<QuantityByYearResponse> quantityByYearResponses = billDetailRepository.getTotalQuantitySoldByYear();
        List<ProductInventoryDTO> productInventoryDTOS = productRepository.getInventoryByProduct();
        List<BillStatusCountDTO> getBillStatusCounts = billRepository.getBillStatusCounts();
        List<ProductQuantityDTO> productQuantityDTOS = productRepository.findProductWithTotalQuantityLessThanTenNative();
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("revenueResponse",revenueResponses);
                    put("getQuantityByYear",quantityByYearResponses);
                    put("productInventoryDTOS",productInventoryDTOS);
                    put("getBillStatusCounts",getBillStatusCounts);
                    put("productQuantityDTOS",productQuantityDTOS);
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
