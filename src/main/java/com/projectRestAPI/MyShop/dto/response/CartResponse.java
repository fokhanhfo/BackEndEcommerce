package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
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
    private Integer status;
}
