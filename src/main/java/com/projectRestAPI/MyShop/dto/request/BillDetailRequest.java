package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

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
