package com.projectRestAPI.MyShop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipping_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;
    private String phone;
    private String addressDetail;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
