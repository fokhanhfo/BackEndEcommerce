package com.projectRestAPI.MyShop.model.bill;

import com.projectRestAPI.MyShop.model.Bill;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Entity
@Table(name = "Payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String moTa;
    private String maGiaoDich;
    private BigDecimal soTien;
    private boolean trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "form_of_payment_id")
    private FormOfPayment formOfPayment;
}
