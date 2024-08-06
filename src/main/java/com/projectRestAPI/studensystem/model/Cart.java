package com.projectRestAPI.studensystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "Cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cart extends BaseEntity{
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
