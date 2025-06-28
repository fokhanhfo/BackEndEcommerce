package com.projectRestAPI.MyShop.dto.response.CartResponse;

import com.projectRestAPI.MyShop.dto.response.ColorResponse;
import com.projectRestAPI.MyShop.dto.response.ProductDetailResponse;
import com.projectRestAPI.MyShop.dto.response.SizeResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartResponse {
    private Long id;
    private Integer quantity;
    private ProductDetailCartResponse productDetail;

    private ColorResponse color;

    private SizeResponse size;

    private Integer status;
}
