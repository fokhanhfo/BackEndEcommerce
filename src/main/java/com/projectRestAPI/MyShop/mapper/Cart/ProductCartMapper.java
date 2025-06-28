package com.projectRestAPI.MyShop.mapper.Cart;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.response.CartResponse.ProductCartResponse;
import com.projectRestAPI.MyShop.mapper.CategoryMapper;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductDiscountPeriodCartMapper.class})
public interface ProductCartMapper {
    Product toProduct(ProductRequest productRequest);

    @Mapping(target = "category", source = "category")
    ProductCartResponse toProductCartResponse(Product product);

    List<ProductCartResponse> toListProductCartResponse(List<Product> products);

}
