package com.projectRestAPI.studensystem.dto.response;

import com.projectRestAPI.studensystem.model.Bill;
import com.projectRestAPI.studensystem.model.BillDetail;
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
    private Long user_id;
    private List<BillDetailResponse> billDetail;
}
