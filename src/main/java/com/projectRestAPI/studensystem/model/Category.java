package com.projectRestAPI.studensystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category extends BaseEntity {
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

}
