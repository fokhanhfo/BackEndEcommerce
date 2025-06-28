package com.projectRestAPI.MyShop.dto.Dashboard;

import java.math.BigDecimal;

public interface ProductInventoryDTO {
    Long getId();
    String getName();

    BigDecimal getSelling();

    BigDecimal getImport();
    Integer getQuantity();
}
