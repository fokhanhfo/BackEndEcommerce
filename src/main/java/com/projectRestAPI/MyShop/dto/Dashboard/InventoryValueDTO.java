package com.projectRestAPI.MyShop.dto.Dashboard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class InventoryValueDTO {
    private Long productId;
    private String productName;
    private Integer totalQuantity;
    private BigDecimal totalImportValue;
    private BigDecimal totalSellingValue;
}