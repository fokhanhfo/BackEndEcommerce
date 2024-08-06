package com.projectRestAPI.studensystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCart {
    CART_SELECT(1),
    CART_NOT_SELECT(0);

    private final Integer status;
}
