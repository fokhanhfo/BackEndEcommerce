package com.projectRestAPI.MyShop.mapper.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toDiscount(DiscountRequest discountRequest);

    @Mapping(target = "discountUsers" , ignore = true)
    DiscountResponse toDiscountResponse(Discount discount);

    List<DiscountResponse> toListDiscountResponse(List<Discount> discountList);

}
