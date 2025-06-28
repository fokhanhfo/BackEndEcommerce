package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusBill {
    PENDING(0),
    PREPARING(1),
    SHIPPING(2),
    WAITING_FOR_DELIVERY(3),
    CONFIRM_COMPLETION(4),
    COMPLETED(5),
    CANCELLED(6),
    RETURNED(7);

    private final Integer status;
}
