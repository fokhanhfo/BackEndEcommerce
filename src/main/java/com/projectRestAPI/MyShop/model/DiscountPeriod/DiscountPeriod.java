package com.projectRestAPI.MyShop.model.DiscountPeriod;

import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @Column(name = "percentage_value")
    private Integer percentageValue;

    @Column(name = "status")
    private Integer status;
}
