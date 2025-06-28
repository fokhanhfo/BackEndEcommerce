package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountStatus {
    INACTIVE(0),//chưa hoạt động
    ACTIVE(1);// đang hoạt động
//    EXPIRED(2),// đã hết hạn
//    CANCELED(1);//đã bị hủy

    private final Integer value;

}
