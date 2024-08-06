package com.projectRestAPI.studensystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusProduct {
    PRODUCT_ACTIVE(1),
    PRODUCT_NOT_ACTIVE(0);

    private final Integer status;


}
