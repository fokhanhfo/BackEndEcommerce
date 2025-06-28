package com.projectRestAPI.MyShop.dto.ProductStatistics;

import java.math.BigDecimal;

public interface ProductSalesView {
    Long getId();
    String getName();
    BigDecimal getImportPrice();
    BigDecimal getSellingPrice();
    Long getTotalSold();
}
