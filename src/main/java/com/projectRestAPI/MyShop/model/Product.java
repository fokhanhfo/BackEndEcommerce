package com.projectRestAPI.MyShop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "Product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity {
    @Column(name="name")
    private String name;
    @Column(name="detail", length = 500)
    private String detail;
    @Column(name="status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product")
    private List<ProductDiscountPeriod> productDiscountPeriods;

}
