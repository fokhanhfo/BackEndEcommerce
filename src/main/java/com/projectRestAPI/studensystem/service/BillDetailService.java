package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.model.BillDetail;

import java.util.List;

public interface BillDetailService extends BaseService<BillDetail,Long> {
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails);
}
