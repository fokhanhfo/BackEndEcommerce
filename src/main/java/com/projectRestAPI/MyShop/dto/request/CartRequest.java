package com.projectRestAPI.MyShop.dto.request;

import com.projectRestAPI.MyShop.model.SanPham.Size;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

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

    private BigDecimal discountPrice;

    private ProductDetailRequest productDetail;

    private Integer status;
}
