package com.projectRestAPI.MyShop.enums;

public enum DiscountStatus {
    INACTIVE(0),
    ACTIVE(1),
    EXPIRED(2),
    CANCELED(3);

    private final int value;

    DiscountStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
