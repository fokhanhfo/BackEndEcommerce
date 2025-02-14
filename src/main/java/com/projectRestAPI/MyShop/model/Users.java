package com.projectRestAPI.MyShop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @ManyToMany
    List<Roles> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_image_id", unique = true)
    private UserImage userImage;
}
