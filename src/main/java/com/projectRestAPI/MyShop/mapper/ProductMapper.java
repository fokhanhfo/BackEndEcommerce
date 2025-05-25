package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.ProductDiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductDetailMapper.class, CategoryMapper.class, ProductDiscountPeriodMapper.class})
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    @Mapping(target = "category", source = "category")
    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toListProductResponse(List<Product> products);

}
