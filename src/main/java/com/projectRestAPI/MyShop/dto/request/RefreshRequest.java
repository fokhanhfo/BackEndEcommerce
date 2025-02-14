package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshRequest {
    private String token;
}
