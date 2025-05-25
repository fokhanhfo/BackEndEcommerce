package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusBill {
    BILL_PENDING(0),
    BILL_COMPLETED(1),
    BILL_SHIPPING(2),
    BILL_AWAITING_DELIVERY(3),
    BILL_RETURN_REFUND(4),
    BILL_CANCEL(5);



    private final Integer status;
}
