package com.projectRestAPI.MyShop.dto.response.Discount;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountPeriodProductResponse {
    private Long id;

    private String discountPeriodCode;

    private String discountPeriodName;

    private Integer minPercentageValue;

    private Integer maxPercentageValue;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

}
