package com.projectRestAPI.MyShop.mapper.DiscountPeriod;

import com.projectRestAPI.MyShop.dto.response.Discount.ProductDiscountPeriodResponse;
import com.projectRestAPI.MyShop.mapper.ProductMapper;
import com.projectRestAPI.MyShop.mapper.ProductShortDiscountMapper;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProductShortDiscountMapper.class, DiscountPeriodProductMapper.class})
public interface ProductDiscountPeriodMapper {
//    ProductDiscountPeriod toProductDiscountPeriod(ProductDiscountPeriod productDiscountPeriod);

    @Mapping(target = "product" , source = "product")
    ProductDiscountPeriodResponse toProductDiscountPeriodResponse(ProductDiscountPeriod productDiscountPeriod);

    List<ProductDiscountPeriodResponse> toListProductDiscountPeriodResponse(List<ProductDiscountPeriod> productDiscountPeriods);
}
