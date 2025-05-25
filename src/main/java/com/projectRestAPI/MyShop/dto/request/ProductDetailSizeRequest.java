package com.projectRestAPI.MyShop.dto.request;

import com.projectRestAPI.MyShop.dto.response.SizeResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailSizeRequest {
    private Long id;
    private SizeRequest size;
    private Integer quantity;
}
