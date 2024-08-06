package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshRequest {
    private String token;
}
