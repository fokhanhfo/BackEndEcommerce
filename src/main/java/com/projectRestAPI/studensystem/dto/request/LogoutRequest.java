package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LogoutRequest {
    private String token;
}
