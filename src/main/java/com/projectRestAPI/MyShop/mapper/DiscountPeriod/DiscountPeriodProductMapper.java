package com.projectRestAPI.MyShop.mapper.DiscountPeriod;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountPeriodRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodProductResponse;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = ProductDiscountPeriodMapper.class)
public interface DiscountPeriodProductMapper {
    DiscountPeriod toDiscountPeriod(DiscountPeriodRequest discountPeriodRequest);

//    @Mapping(target = "discountUsers" , ignore = true)
    DiscountPeriodProductResponse toDiscountPeriodProductResponse(DiscountPeriod discountPeriod);

    List<DiscountPeriodProductResponse> toListDiscountPeriodProductResponse(List<DiscountPeriod> discountList);
}
