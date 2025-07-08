package com.projectRestAPI.MyShop.model.DiscountPeriod;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Entity
@Table(name = "DiscountPeriod")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiscountPeriod extends BaseEntity {
    @Column(name = "discount_period_code")
    private String discountPeriodCode;

    @Column(name = "discount_period_name")
    private String discountPeriodName;

    @Column(name = "min_percentage_value")
    private Integer minPercentageValue;

    @Column(name = "max_percentage_value")
    private Integer maxPercentageValue;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "discountPeriod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductDiscountPeriod> productDiscountPeriods;
}
