package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "Cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Cart extends BaseEntity{
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
