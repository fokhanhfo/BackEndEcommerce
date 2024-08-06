package com.projectRestAPI.studensystem.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequest {
    private String username;
    @Size(min = 3 ,message = "VALID_PASSWORD")
    private String password;
}
