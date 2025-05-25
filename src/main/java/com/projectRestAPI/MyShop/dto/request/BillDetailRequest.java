package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillDetailRequest {
    private Long id;

    private Long productDetailId;

    private Integer size;

    private String color;

    private Integer quantity;
}
