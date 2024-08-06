package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartRequest {
    private Long id;

    private Integer quantity;

    private Long product;

    private Integer status;
}
