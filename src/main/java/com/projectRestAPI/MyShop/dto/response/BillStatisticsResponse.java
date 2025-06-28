package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyOrderCount;
import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyRevenue;
import com.projectRestAPI.MyShop.dto.BillStatistics.OrderStatusRatio;
import com.projectRestAPI.MyShop.dto.BillStatistics.PaymentMethodRevenue;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillStatisticsResponse {
    private List<MonthlyRevenue> monthlyRevenue;
    private List<MonthlyOrderCount> monthlyOrderCount;
    private List<PaymentMethodRevenue> revenueByPaymentMethod;
    private List<OrderStatusRatio> orderStatusRatio;
}
