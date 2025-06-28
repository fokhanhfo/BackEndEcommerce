package com.projectRestAPI.MyShop.dto.response.CartResponse;

import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodProductResponse;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountPeriodResponse;
import com.projectRestAPI.MyShop.dto.response.ProductShortDiscountResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class  ProductDiscountPeriodCartResponse {
    private Long id;
    private Integer percentageValue;
    private DiscountPeriodProductResponse discountPeriod;
}