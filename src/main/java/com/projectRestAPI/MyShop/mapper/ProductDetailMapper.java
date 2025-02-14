package com.projectRestAPI.MyShop.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.request.*;
import com.projectRestAPI.MyShop.dto.response.ProductDetailResponse;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ColorMapper.class, SizeMapper.class, ProductShortMapper.class})
public interface ProductDetailMapper {

    ProductDetail toProductDetail(ProductDetailRequest productDetailRequest);

    @Mapping(target = "imagesUrl", source = "image", qualifiedByName = "mapImageToUrl")
    @Mapping(target = "product" , source = "product")
    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);

    List<ProductDetailResponse> toListProductDetailResponse(List<ProductDetail> productDetails);


    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    ProductDetailRequest toProductDetailRequest(ProductDetailMappingRequest productDetailMappingRequest);

    @Named("mapImageToUrl")
    default String mapImageToUrl(Image image) {
        return image != null ? "http://localhost:8080/image/" + image.getName() : null;
    }
}
