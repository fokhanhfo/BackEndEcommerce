package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ColorResponse {
    private Long id;
    private String name;
    private String key;
    private Integer status;
}
