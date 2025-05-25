package com.projectRestAPI.MyShop.dto.request;

import com.projectRestAPI.MyShop.model.SanPham.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartRequest {
    private Long id;

    private Integer quantity;

    private SizeRequest size;

    private ColorRequest color;

    private ProductDetailRequest productDetail;

    private Integer status;
}
