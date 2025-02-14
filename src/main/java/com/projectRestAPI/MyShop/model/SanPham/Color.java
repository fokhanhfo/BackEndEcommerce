package com.projectRestAPI.MyShop.model.SanPham;

import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "Color")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Color extends BaseEntity {
    private String name;
    @Column(name = "color_key")
    private String key;
    private Integer status;
}
