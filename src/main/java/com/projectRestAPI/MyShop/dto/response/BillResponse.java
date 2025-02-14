package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillResponse {
    private Long id;
    private String phone;
    private String email;
    private int status;
    private String address;
    private BigDecimal total_price;
    private LocalDateTime createdDate;
    private UserResponse userResponse;
    private List<BillDetailResponse> billDetail;
}
