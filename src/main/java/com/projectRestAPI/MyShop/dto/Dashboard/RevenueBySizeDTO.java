package com.projectRestAPI.MyShop.dto.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RevenueBySizeDTO {
    private String size;
    private BigDecimal revenue;
}
