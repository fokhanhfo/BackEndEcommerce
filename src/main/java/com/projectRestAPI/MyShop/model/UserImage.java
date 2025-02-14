package com.projectRestAPI.MyShop.model;

import com.projectRestAPI.MyShop.model.SanPham.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "UserImage")
public class UserImage extends BaseEntity{
    private String name;
    private String type;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] file;

    @OneToOne(mappedBy = "userImage", cascade = CascadeType.ALL)
    private Users user;
}
