package com.projectRestAPI.MyShop.mapper.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = DiscountUserShortMapper.class)
public interface DiscountShortMapper {
    Discount toDiscount(DiscountRequest discountRequest);

    DiscountResponse toDiscountResponse(Discount discount);

    List<DiscountResponse> toListDiscountResponse(List<Discount> discountList);
}
