package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "Users")
public class Users extends BaseEntity {
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "type")
    private Integer typeLogin;
    @Column(name = "enable", nullable = false)
    private Boolean enable = false;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;


    @ManyToMany
    List<Roles> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image_id", unique = true)
    private UserImage userImage;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscountUser> discountUsers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingAddress> shippingAddresses;


    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }



}
