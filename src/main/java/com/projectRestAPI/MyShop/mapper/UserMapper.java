package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.UserResponse;
import com.projectRestAPI.MyShop.model.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(Users users);

    Users toUsers(UserRequest userRequest);
    List<Users> toListUsers(List<UserRequest> userRequests);

    List<UserResponse> toListUserResponse(List<Users> users);
}
