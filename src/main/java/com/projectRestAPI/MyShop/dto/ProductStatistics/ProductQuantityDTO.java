package com.projectRestAPI.MyShop.dto.ProductStatistics;

import java.math.BigDecimal;

public interface ProductQuantityDTO {
    Long getId();
    String getName();
    BigDecimal getImportPrice();
    BigDecimal getSellingPrice();
    Long getTotalQuantity();
}
