package com.projectRestAPI.MyShop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoaiHinhThuc {
    TIEN_MAT(0),
    CHUYEN_KHOAN(1),
    THANH_TOAN_KHI_NHAN_HANG(2);

    private final Integer paymentMethod;
}
