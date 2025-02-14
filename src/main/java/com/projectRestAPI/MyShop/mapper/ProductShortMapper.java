package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.response.ProductShortResponse;
import com.projectRestAPI.MyShop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductShortMapper {
    @Mapping(target = "category" , source = "category")
    ProductShortResponse toProductShortResponse(Product product);
}
