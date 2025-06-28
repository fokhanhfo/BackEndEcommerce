package com.projectRestAPI.MyShop.dto.BillStatistics;

import java.math.BigDecimal;

public interface MonthlyRevenue {
    Integer getMonth();
    BigDecimal getRevenue();
}
