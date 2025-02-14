package com.projectRestAPI.MyShop.model.SanPham;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.Product;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Entity
@Table(name = "ProductDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetail extends BaseEntity {
    @Column(name = "import_price")
    private BigDecimal importPrice;
    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

//    public List<String> getImageNames(){
//        return images.stream().map(Image::getName).collect(Collectors.toList());
//    }
}
