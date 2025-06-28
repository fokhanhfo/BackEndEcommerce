package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import com.projectRestAPI.MyShop.mapper.DiscountPeriod.ProductDiscountPeriodMapper;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductDetailMapper.class, CategoryMapper.class, ProductDiscountPeriodMapper.class})
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    @Mapping(target = "category", source = "category")
    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toListProductResponse(List<Product> products);

}
