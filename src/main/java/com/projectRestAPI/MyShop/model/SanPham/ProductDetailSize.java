package com.projectRestAPI.MyShop.model.SanPham;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "ProductDetailSize")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailSize extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    @JsonBackReference
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
