package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.response.ColorResponse;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color toColor(Color color);

    Color toColor(ColorRequest colorRequest);

    ColorResponse toColorResponse(Color color);
}
