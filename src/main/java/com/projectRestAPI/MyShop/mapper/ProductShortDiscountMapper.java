package com.projectRestAPI.MyShop.mapper;


import com.projectRestAPI.MyShop.dto.response.ProductShortDiscountResponse;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.ProductDiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductDetailMapper.class, CategoryMapper.class, ProductDiscountPeriodMapper.class})
public interface ProductShortDiscountMapper {

    @Mapping(target = "category" , source = "category")
    ProductShortDiscountResponse toProductShortResponse(Product product);

    List<ProductShortDiscountResponse> toListProductShortDiscountResponse(List<Product> products);
}
