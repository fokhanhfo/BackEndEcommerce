package com.projectRestAPI.studensystem.dto.response;
import com.projectRestAPI.studensystem.model.BillDetail;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BillDetailResponse {
    private Long id;
    private String phone;
    private String email;
    private String address;
    private BigDecimal total_price;
    private int status;
    private String name_user;
    private List<BillDetail> billDetails;
}
