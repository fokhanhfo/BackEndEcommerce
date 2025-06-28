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
    private String fullName;
    private String phone;
    private String email;
    private Integer status;
    private String address;
    private BigDecimal total_price;
    private Integer payMethod;
    private String note;
    private BigDecimal discountShip;
    private BigDecimal discountUser;
    private LocalDateTime createdDate;
    private UserResponse user;
    private List<BillDetailResponse> billDetail;
}
