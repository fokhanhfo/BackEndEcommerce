package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartRequest {
    private Long id;

    private Integer quantity;

    private ProductDetailRequest productDetail;

    private Integer status;
}
