package com.projectRestAPI.MyShop.mapper.Cart;

import com.projectRestAPI.MyShop.dto.request.ProductDetailMappingRequest;
import com.projectRestAPI.MyShop.dto.request.ProductDetailRequest;
import com.projectRestAPI.MyShop.dto.response.CartResponse.ProductDetailCartResponse;
import com.projectRestAPI.MyShop.mapper.ColorMapper;
import com.projectRestAPI.MyShop.mapper.ImageMapper;
import com.projectRestAPI.MyShop.mapper.ProductShortMapper;
import com.projectRestAPI.MyShop.mapper.SizeMapper;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ColorMapper.class, SizeMapper.class, ProductCartMapper.class, ImageMapper.class})
public interface ProductDetailCartMapper {

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
    ProductDetailCartResponse toProductDetailCartResponse(ProductDetail productDetail);

    List<ProductDetailCartResponse> toListProductDetailCartResponse(List<ProductDetail> productDetails);


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
