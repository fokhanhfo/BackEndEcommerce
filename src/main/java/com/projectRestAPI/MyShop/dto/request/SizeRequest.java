package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SizeRequest {
    private Long id;
    private String name;
    private Integer status;
}
