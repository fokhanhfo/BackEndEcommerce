package com.projectRestAPI.MyShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "OtpVerification")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OtpVerification extends BaseEntity {
    private String email;
    private String otp;
    private LocalDateTime expirationTime;
    private String purpose;
}
