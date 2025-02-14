package com.projectRestAPI.MyShop.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillRequest {
    private String phone;

    private String email;

    private String address;

    private List<CartRequest> cartRequests;
}
