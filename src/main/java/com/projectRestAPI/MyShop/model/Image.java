package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Table(name = "ImageProduct")
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Image extends BaseEntity {
    private String name;
    private String type;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "is_main_product")
    private boolean mainProduct;

    @Column(name = "is_main_color")
    private boolean mainColor;

}
