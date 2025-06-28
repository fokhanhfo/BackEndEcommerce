package com.projectRestAPI.MyShop.dto.response;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillDetailResponse {
    private Long id;
    private ProductDetailResponse productDetail;;
    private Integer quantity;
    private String size;
    private String color;
    private BigDecimal sellingPrice;
    private BigDecimal discountPrice;
}
