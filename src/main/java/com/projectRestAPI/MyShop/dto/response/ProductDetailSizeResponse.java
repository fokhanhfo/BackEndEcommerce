package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailSizeResponse {
    private Long id;
    private SizeResponse size;
    private Integer quantity;
}
