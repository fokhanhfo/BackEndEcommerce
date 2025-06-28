package com.projectRestAPI.MyShop.mapper.Cart;

import com.projectRestAPI.MyShop.dto.response.CartResponse.ProductDiscountPeriodCartResponse;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.DiscountPeriodMapper;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.DiscountPeriodProductMapper;
import com.projectRestAPI.MyShop.mapper.ProductShortDiscountMapper;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {DiscountPeriodMapper.class, ProductShortDiscountMapper.class, DiscountPeriodProductMapper.class})
public interface ProductDiscountPeriodCartMapper {
//    ProductDiscountPeriod toProductDiscountPeriod(ProductDiscountPeriod productDiscountPeriod);

    ProductDiscountPeriodCartResponse toProductDiscountPeriodCartResponse(ProductDiscountPeriod productDiscountPeriod);

    List<ProductDiscountPeriodCartResponse> toListProductDiscountPeriodCartResponse(List<ProductDiscountPeriod> productDiscountPeriods);
}
