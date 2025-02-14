package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.model.Roles;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {
    private Long id;
    private String fullName;

    private Date birthday;

    private Boolean gender;

    private String address;

    private String email;

    private String phone;

    private String username;

    private List<Roles> roles;
}
