package com.projectRestAPI.MyShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BestSellingProductDTO {
    private Long productId;
    private String productName;
    private Long quantitySold;
}
