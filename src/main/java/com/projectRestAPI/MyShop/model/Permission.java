package com.projectRestAPI.MyShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "Permission")
public class Permission extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
