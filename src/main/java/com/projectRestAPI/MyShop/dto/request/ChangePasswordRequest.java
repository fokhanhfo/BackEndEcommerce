package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String otp;
    private String email;
    private String newEmail;
    private String newPhone;
}
