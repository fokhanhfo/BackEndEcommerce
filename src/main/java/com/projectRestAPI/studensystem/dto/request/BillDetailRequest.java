package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillDetailRequest {
    private Long id;

    private Long productId;

    private Integer quantity;
}
