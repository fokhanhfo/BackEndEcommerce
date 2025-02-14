package com.projectRestAPI.MyShop.model.SanPham;

import com.projectRestAPI.MyShop.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Table(name = "Size")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Size extends BaseEntity {
    private String name;
    private Integer status;
}
