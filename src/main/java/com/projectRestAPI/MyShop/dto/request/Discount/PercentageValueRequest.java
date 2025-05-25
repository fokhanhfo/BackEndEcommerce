package com.projectRestAPI.MyShop.dto.request.Discount;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PercentageValueRequest {
    private Integer percentageValue;
    private ProductRequest product;
}
