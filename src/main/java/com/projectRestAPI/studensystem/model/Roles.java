package com.projectRestAPI.studensystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Roles")
public class Roles extends BaseEntity{
    @Column(name = "name")
    private String name;

    @ManyToMany
    private List<Permission> permissions;
}
