package com.projectRestAPI.MyShop.dto.response.Discount;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import com.projectRestAPI.MyShop.dto.response.ProductShortDiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ProductShortResponse;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDiscountPeriodResponse {
    private Long id;
    private ProductShortDiscountResponse product;
    private Integer percentageValue;
    private DiscountPeriodResponse discountPeriod;
}