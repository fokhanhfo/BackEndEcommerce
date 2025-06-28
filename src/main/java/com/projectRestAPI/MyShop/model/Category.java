package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.SanPham.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Category extends BaseEntity {
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;


}
