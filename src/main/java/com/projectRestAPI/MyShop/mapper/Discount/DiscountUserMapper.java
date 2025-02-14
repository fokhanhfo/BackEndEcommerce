package com.projectRestAPI.MyShop.mapper.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountUserRequest;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountUserMapper {
    DiscountUser toDiscountUser(DiscountUserRequest discountUserRequest);
}
