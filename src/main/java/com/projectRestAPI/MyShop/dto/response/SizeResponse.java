package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SizeResponse {
    private Integer id;
    private String name;
    private Integer status;
}
