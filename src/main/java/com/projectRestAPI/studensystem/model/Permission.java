package com.projectRestAPI.studensystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Permission")
public class Permission extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
