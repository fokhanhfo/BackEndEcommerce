package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.model.BillDetail;
import com.projectRestAPI.MyShop.repository.BillDetailRepository;
import com.projectRestAPI.MyShop.service.BillDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail,Long, BillDetailRepository> implements BillDetailService {
    @Override
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails) {
        return repository.saveAll(billDetails);
    }
}
