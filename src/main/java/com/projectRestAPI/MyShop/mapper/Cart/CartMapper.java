package com.projectRestAPI.MyShop.mapper.Cart;

import com.projectRestAPI.MyShop.dto.request.CartRequest;
import com.projectRestAPI.MyShop.dto.response.CartResponse.CartResponse;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDetailCartMapper.class})
public interface CartMapper {
//    @Mapping(target = "productDetail.imagesUrl", source = "productDetail.image", qualifiedByName = "mapImageToUrl")

    Cart toCart(CartRequest cartRequest);

    List<Cart> toListCart(List<CartRequest> cartRequests);

    CartResponse toCartResponse(Cart cart);

    List<CartResponse> toListCartResponse(List<Cart> carts);

}
