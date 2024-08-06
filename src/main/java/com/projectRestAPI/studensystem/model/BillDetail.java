package com.projectRestAPI.studensystem.model;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Entity
@Table(name = "BillDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetail extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

}
