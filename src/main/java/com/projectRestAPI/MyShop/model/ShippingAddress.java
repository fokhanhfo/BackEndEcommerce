package com.projectRestAPI.MyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "shipping_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ShippingAddress extends BaseEntity {
    private String recipientName;
    private String phone;
    private String idProvince;
    private String idDistrict;
    private String idCommune;
    private String addressDetail;
    @Column(name = "is_default")
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
