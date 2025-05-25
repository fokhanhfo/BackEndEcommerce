package com.projectRestAPI.MyShop.mapper.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountUserResponse;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = DiscountMapper.class)
public interface DiscountUserMapper {
    DiscountUser toDiscountUser(DiscountUserRequest discountUserRequest);

    DiscountUserResponse toDiscountUserResponse(DiscountUser discountUser);

    List<DiscountUserResponse> toListDiscountUserResponse(List<DiscountUser> discountUsers);
}
