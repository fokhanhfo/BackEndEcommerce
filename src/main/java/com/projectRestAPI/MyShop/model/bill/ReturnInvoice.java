package com.projectRestAPI.MyShop.model.bill;

import com.projectRestAPI.MyShop.model.Bill;
import com.projectRestAPI.MyShop.model.BillDetail;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Entity
@Table(name = "ReturnInvoice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ma;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String emailNguoiNhan;
    private String diaChiNguoiNhan;
    private BigDecimal tongTien;
    private BigDecimal tongTienPhieuGiamGiaCu;
    private BigDecimal tongTienPhieuGiamGiaMoi;
    private BigDecimal tongTienTraKhach;
    private String ghiChu;

    @OneToMany(mappedBy = "returnInvoice", cascade = CascadeType.ALL)
    private List<BillDetail> billDetails;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Bill bill;
}
