package com.projectRestAPI.MyShop.model;


import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.bill.ReturnInvoice;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Entity
@Table(name = "BillDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetail extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "return_invoice_id")
    private ReturnInvoice returnInvoice;

}
