package com.projectRestAPI.MyShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Table(name = "InvalidatedToken")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class InvalidatedToken extends BaseEntity{
    private String idCode;
    private Date expiryTime;
}
