package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.SanPham.Size;
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
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
