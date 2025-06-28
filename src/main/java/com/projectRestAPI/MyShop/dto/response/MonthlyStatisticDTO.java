package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MonthlyStatisticDTO {
    private int month;
    private BigDecimal revenue;
    private int totalOrders;
    private int totalCustomers;
}
