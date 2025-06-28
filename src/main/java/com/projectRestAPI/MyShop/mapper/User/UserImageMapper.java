package com.projectRestAPI.MyShop.mapper.User;

import com.projectRestAPI.MyShop.dto.response.UserImageResponse;
import com.projectRestAPI.MyShop.model.UserImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserImageMapper {
    @Mapping(target = "userImage"
            , source = "name"
            , qualifiedByName = "mapImageUserToUrl"
    )
    UserImageResponse toUserImageResponse(UserImage userImage);

    @Named("mapImageUserToUrl")
    default String mapImageUserToUrl(String image) {
        return (image == null || image.isEmpty())
                ? null
                : "http://localhost:8080/userImage/" + image;
    }
}
