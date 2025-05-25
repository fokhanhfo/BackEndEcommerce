package com.projectRestAPI.MyShop.mapper.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountUserResponse;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountUserShortMapper {
    DiscountUser toDiscountUser(DiscountUserRequest discountUserRequest);

    @Mapping(target = "discount" , ignore = true)
    DiscountUserResponse toDiscountUserResponse(DiscountUser discountUser);

    List<DiscountUserResponse> toListDiscountUserResponse(List<DiscountUser> discountUsers);
}
