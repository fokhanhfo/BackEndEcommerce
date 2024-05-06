package com.projectRestAPI.studensystem.dto.response;

import com.projectRestAPI.studensystem.dto.request.RolesRequest;
import com.projectRestAPI.studensystem.dto.response.RolesResponse;
import com.projectRestAPI.studensystem.model.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private String password;

    private List<Roles> roles;
}
