package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RevenueByCategoryDTO {
    private String categoryName;
    private int year;
    private BigDecimal revenue;
}
