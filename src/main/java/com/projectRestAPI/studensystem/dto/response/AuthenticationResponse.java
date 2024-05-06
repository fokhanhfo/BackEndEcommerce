package com.projectRestAPI.studensystem.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
}
