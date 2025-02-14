package com.projectRestAPI.MyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "Roles")
public class Roles extends BaseEntity{
    @Column(name = "name")
    private String name;

    @ManyToMany
    private List<Permission> permissions;
}
