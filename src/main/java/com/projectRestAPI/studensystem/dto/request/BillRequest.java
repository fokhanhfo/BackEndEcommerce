package com.projectRestAPI.studensystem.dto.request;

import com.projectRestAPI.studensystem.model.Cart;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillRequest {
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    private List<CartRequest> cartRequests;
}
