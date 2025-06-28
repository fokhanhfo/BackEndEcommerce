package com.projectRestAPI.MyShop.model.DiscountPeriod;

import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "ProductDiscountPeriod")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDiscountPeriod extends BaseEntity {

    @Column(name = "percentage_value")
    private Integer percentageValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "discount_period_id")
    private DiscountPeriod discountPeriod;
}
