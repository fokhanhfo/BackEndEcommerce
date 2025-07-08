package com.projectRestAPI.MyShop.utils;

public class StatusBillUltil {
    public static String getStatusDescription(Integer status) {
        if (status == null) return "Không xác định";

        switch (status) {
            case 0:
                return "Chờ xác nhận";
            case 1:
                return "Đang chuẩn bị";
            case 2:
                return "Đang giao hàng";
            case 3:
                return "Chờ giao";
            case 4:
                return "Xác nhận hoàn thành";
            case 5:
                return "Đã hoàn thành";
            case 6:
                return "Đã hủy";
            case 7:
                return "Đã trả hàng";
            default:
                return "Không xác định";
        }
    }
}
