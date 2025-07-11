package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillRequest {
    private String fullName;

    private String phone;

    private String email;

    private String address;

    private String note;

    private Integer paymentMethod;

    private BigDecimal total_price;

    private BigDecimal discountShip;

    private BigDecimal discountUser;

    private List<Long> discountId;

    private String otp;

    private List<CartRequest> cartRequests;
}
