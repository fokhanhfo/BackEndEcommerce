package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.response.CartResponse;
import com.projectRestAPI.MyShop.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDetailMapper.class})
public interface CartMapper {
    @Mapping(target = "productDetail.imagesUrl", source = "productDetail.image", qualifiedByName = "mapImageToUrl")
    CartResponse toCartResponse(Cart cart);

    List<CartResponse> toListCartResponse(List<Cart> carts);

}
