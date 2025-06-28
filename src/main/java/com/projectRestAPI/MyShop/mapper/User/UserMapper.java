package com.projectRestAPI.MyShop.mapper.User;

import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.UserImage;
import com.projectRestAPI.MyShop.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UserImageMapper.class})
public interface UserMapper {
    @Mapping(target = "userImage", source = "userImage")
    UserResponse toUserResponse(Users users);

    Users toUsers(UserRequest userRequest);
    List<Users> toListUsers(List<UserRequest> userRequests);

    List<UserResponse> toListUserResponse(List<Users> users);
}
