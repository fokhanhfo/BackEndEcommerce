package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.model.BillDetail;
import com.projectRestAPI.studensystem.repository.BillDetailRepository;
import com.projectRestAPI.studensystem.service.BillDetailService;
import com.projectRestAPI.studensystem.service.BillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillDetailServiceImpl extends BaseServiceImpl<BillDetail,Long, BillDetailRepository> implements BillDetailService {
    @Override
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails) {
        return repository.saveAll(billDetails);
    }
}
