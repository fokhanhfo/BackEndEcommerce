package com.projectRestAPI.MyShop.dto.response.Discount;

import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiscountPeriodResponse {
    private Long id;

    private String discountPeriodCode;

    private String discountPeriodName;

    private Integer minPercentageValue;

    private Integer maxPercentageValue;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private List<ProductDiscountPeriodResponse> productDiscountPeriods;
}
