package com.projectRestAPI.MyShop.dto.request;

import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductShortRequest {
    private Long id;
    private String name;
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private String detail;
    private CategoryResponse category;
    private Integer status;
}
