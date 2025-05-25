package com.projectRestAPI.MyShop.mapper.Bill;

import com.projectRestAPI.MyShop.dto.response.BillResponse;
import com.projectRestAPI.MyShop.model.Bill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {BillDetailMapper.class})
public interface BillMapper {
    BillResponse toBillResponse(Bill bill);

    List<BillResponse> toListBillResponse(List<Bill> bills);
}
