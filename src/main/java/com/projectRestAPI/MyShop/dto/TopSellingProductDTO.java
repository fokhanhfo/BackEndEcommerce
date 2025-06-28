package com.projectRestAPI.MyShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopSellingProductDTO {
    private Long productId;
    private String productName;
    private Long totalQuantitySold;
}
