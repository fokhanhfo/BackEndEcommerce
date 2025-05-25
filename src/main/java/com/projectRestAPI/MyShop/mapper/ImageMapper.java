package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.ImageRequest;
import com.projectRestAPI.MyShop.dto.response.ImageResponse;
import com.projectRestAPI.MyShop.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "imageUrl"
            , source = "name"
            , qualifiedByName = "mapImageToUrl"
    )
    @Mapping(target = "productDetail" , ignore = true)
    ImageResponse toImageResponse(Image image);

    List<ImageResponse> toListImageResponse(List<Image> image);

    @Named("mapImageToUrl")
    default String mapImageToUrl(String image) {
        return (image == null || image.isEmpty())
                ? null
                : "http://localhost:8080/image/" + image;
    }
}
