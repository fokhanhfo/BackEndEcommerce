package com.projectRestAPI.MyShop.dto.response.CartResponse;

import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import com.projectRestAPI.MyShop.dto.response.Discount.ProductDiscountPeriodResponse;
import com.projectRestAPI.MyShop.dto.response.ProductDetailResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductCartResponse {
    private Long id;
    private String name;
    private String detail;
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private CategoryResponse category;
    private Integer status;
    private List<ProductDiscountPeriodCartResponse> productDiscountPeriods;
}
