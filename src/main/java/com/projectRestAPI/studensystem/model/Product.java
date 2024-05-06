package com.projectRestAPI.studensystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Table(name = "Product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseEntity {
    @Column(name="name")
    private String name;
    @Column(name="detail")
    private String detail;
    @Column(name="price")
    private BigDecimal price;
    @Column(name="quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Image> images;
}
