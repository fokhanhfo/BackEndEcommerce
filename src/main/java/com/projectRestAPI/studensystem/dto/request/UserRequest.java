package com.projectRestAPI.studensystem.dto.request;

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
public class UserRequest {
    private Long id;
    private String fullName;

    private Date birthday;

    private Boolean gender;

    private String address;

    private String email;

    private String phone;

    private String username;

    private String password;

    private List<Long> roles;
}
