package com.projectRestAPI.MyShop.dto.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuantityByYearResponse {
    private int year;
    private long totalQuantity;
}
