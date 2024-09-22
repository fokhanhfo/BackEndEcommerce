package com.projectRestAPI.studensystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(name="detail", length = 500)
    private String detail;
    @Column(name="price")
    private BigDecimal price;
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Image> images;

    public List<String> getImageNames(){
        return images.stream().map(Image::getName).collect(Collectors.toList());
    }
}
