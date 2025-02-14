package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.model.BillDetail;

import java.util.List;

public interface BillDetailService extends BaseService<BillDetail,Long> {
    public List<BillDetail> saveAllBillDetail(List<BillDetail> billDetails);
}
