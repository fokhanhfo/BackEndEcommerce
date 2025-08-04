package com.projectRestAPI.MyShop.dto.request;

import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequest {
    private Long id;
    private String fullName;

    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private Date birthday;

    private Boolean gender;

    private String address;

    private String email;

    private String phone;

    private String username;

    private String password;

    private Integer typeLogin;

    private List<RolesRequest> roles;
}
