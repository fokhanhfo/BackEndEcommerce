package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class IntrospectRequest {
    private String token;
}
