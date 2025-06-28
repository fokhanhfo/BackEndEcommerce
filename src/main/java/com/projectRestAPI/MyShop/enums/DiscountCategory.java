package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountCategory {
    SAN_PHAM(1),
    VAN_CHUYEN(2);


    private final Integer discountCategory;
}
