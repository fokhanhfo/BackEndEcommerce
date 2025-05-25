package com.projectRestAPI.MyShop.dto.request.Discount;

import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDiscountPeriodAllRequest {
    private Long id;
    private List<PercentageValueRequest> percentageValue;
    private DiscountPeriod discountPeriod;
}
