package com.projectRestAPI.MyShop.dto.response;
import lombok.*;

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
