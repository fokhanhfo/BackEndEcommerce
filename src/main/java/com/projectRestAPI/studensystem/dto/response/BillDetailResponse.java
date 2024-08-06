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
    private ProductResponse productId;
    private Integer quantity;
}
