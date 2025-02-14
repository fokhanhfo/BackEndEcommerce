package com.projectRestAPI.MyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
