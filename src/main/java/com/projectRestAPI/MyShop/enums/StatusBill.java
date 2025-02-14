package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusBill {
    BILL_CANCEL(0),
    BILL_PENDING(1),
    BILL_COMPLETED(2);

    private final Integer status;
}
