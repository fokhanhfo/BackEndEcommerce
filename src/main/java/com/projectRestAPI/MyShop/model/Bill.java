package com.projectRestAPI.MyShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Entity
@Table(name = "Bill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill extends BaseEntity{
    @Column(name = "fullName")
    private String fullname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Integer status;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "pay_method")
    private Integer payMethod;

    @Column(name = "total_price")
    private BigDecimal total_price;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private Users user;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillDetail> billDetail;

}
