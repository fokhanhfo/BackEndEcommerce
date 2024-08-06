package com.projectRestAPI.studensystem.dto.param;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductParam {
    private Long categoryId;
    private BigDecimal priceGte;
    private BigDecimal priceLte;
    private Integer status;
    private String sort;
    private String search;
}
