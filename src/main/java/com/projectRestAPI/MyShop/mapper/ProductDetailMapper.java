package com.projectRestAPI.MyShop.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.request.*;
import com.projectRestAPI.MyShop.dto.response.ProductDetailResponse;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ColorMapper.class, SizeMapper.class, ProductShortMapper.class,ImageMapper.class})
public interface ProductDetailMapper {

    List<ProductDetail> toListProductDetail(List<ProductDetail> productDetail);

    ProductDetail toProductDetail(ProductDetail productDetail);

    @Mapping(target = "product" , source = "product")
//    @Mapping(target = "size", ignore = true)
    @Mapping(target = "image",ignore = true)
    ProductDetail toProductDetail(ProductDetailRequest productDetailRequest);

//    @Mapping(target = "imagesUrl"
//            , source = "image"
//            , qualifiedByName = "mapImageToUrl"
//    )
    @Mapping(target = "product" , source = "product")
    @Mapping(target = "image" , source = "image")
    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);

    List<ProductDetailResponse> toListProductDetailResponse(List<ProductDetail> productDetails);


    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    ProductDetailRequest toProductDetailRequest(ProductDetailMappingRequest productDetailMappingRequest);

//    @Named("mapImageToUrl")
//    default List<String> mapImageToUrl(List<Image> image) {
//        if (image == null || image.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return image.stream()
//                .map(image1 -> "http://localhost:8080/image/" + image1.getName())
//                .collect(Collectors.toList());
//    }
}
