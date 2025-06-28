package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountType {
    PHAN_TRAM(1),
    TIEN_MAT(2);


    private final Integer discountType;
}
