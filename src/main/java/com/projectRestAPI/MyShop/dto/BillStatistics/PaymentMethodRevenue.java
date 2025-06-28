package com.projectRestAPI.MyShop.dto.BillStatistics;

import java.math.BigDecimal;

public interface PaymentMethodRevenue {
    Integer getPayMethod();
    BigDecimal getTotalRevenue();
}
