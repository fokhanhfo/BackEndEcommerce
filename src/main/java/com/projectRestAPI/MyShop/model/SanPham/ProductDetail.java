package com.projectRestAPI.MyShop.model.SanPham;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.Image;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Entity
@Table(name = "ProductDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductDetailSize> productDetailSizes;

}
