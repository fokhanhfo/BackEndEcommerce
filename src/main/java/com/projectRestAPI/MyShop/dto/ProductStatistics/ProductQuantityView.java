package com.projectRestAPI.MyShop.dto.ProductStatistics;

import java.math.BigDecimal;

public interface ProductQuantityView {
    Long getId();
    String getName();
    BigDecimal getImportPrice();
    BigDecimal getSellingPrice();
    Long getTotalQuantity();
}
