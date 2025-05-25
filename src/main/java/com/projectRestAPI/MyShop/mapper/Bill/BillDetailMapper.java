package com.projectRestAPI.MyShop.mapper.Bill;

import com.projectRestAPI.MyShop.dto.response.BillDetailResponse;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.BillDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProductDetailMapper.class})
public interface BillDetailMapper {
    BillDetailResponse toBillDetailResponse(BillDetail billDetail);

    List<BillDetailResponse> toListBillDetailResponse(List<BillDetail> billDetails);
}
