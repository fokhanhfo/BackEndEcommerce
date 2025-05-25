package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.Size;
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
    private ProductDetailResponse productDetail;

    private ColorResponse color;

    private SizeResponse size;

    private Integer status;
}
