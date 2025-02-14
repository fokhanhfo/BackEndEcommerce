package com.projectRestAPI.MyShop.model.DiscountPeriod;

import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Entity
@Table(name = "ProductDiscountPeriod")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDisCountPeriod extends BaseEntity {
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "discount_period_id", referencedColumnName = "id")
    private DiscountPeriod discountPeriod;
}
