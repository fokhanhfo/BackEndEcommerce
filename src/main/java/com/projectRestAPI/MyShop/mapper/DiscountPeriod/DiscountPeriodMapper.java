package com.projectRestAPI.MyShop.mapper.DiscountPeriod;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountPeriodRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodResponse;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = ProductDiscountPeriodMapper.class)
public interface DiscountPeriodMapper {
    DiscountPeriod toDiscountPeriod(DiscountPeriodRequest discountPeriodRequest);

//    @Mapping(target = "discountUsers" , ignore = true)
    DiscountPeriodResponse toDiscountPeriodResponse(DiscountPeriod discountPeriod);

    List<DiscountPeriodResponse> toListDiscountPeriodResponse(List<DiscountPeriod> discountList);
}
