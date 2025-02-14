package com.projectRestAPI.MyShop.model.bill;

import com.projectRestAPI.MyShop.enums.LoaiHinhThuc;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "FormOfPayment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormOfPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoaiHinhThuc hinhThuc;

    @OneToMany(mappedBy = "formOfPayment",fetch = FetchType.LAZY)
    private List<Payment> payments;
}
