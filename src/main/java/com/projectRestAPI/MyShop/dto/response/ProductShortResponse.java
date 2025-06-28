package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.response.Discount.ProductDiscountPeriodResponse;
import com.projectRestAPI.MyShop.model.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductShortResponse {
    private Long id;
    private String name;
    private String detail;
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private CategoryResponse category;
    private Integer status;
}
