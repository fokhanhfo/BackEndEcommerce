package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.SizeResponse;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import com.projectRestAPI.MyShop.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size toSize(Size size);
    Size toSize(SizeRequest sizeRequest);

    SizeResponse toSizeResponse(Size size);

}
