package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.response.ProductShortResponse;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.ProductDiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductDiscountPeriodMapper.class})
public interface ProductShortMapper {
    @Mapping(target = "category" , source = "category")
    ProductShortResponse toProductShortResponse(Product product);
}
