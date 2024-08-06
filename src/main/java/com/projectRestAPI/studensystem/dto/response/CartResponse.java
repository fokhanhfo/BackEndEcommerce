package com.projectRestAPI.studensystem.dto.response;

import com.projectRestAPI.studensystem.model.Product;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartResponse {
    private Long id;
    private Integer quantity;
    private ProductResponse product;
    private Integer status;
}
