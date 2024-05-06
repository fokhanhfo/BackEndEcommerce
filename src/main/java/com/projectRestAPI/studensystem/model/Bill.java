package com.projectRestAPI.studensystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Entity
@Table(name = "Bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill extends BaseEntity{
    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private int status;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price")
    private BigDecimal total_price;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private Users user;

    @OneToMany
    @JoinColumn(name = "bill_id")
    private List<BillDetail> billDetail;

}
