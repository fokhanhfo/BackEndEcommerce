package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequest {
    private String username;
    private String password;
}
