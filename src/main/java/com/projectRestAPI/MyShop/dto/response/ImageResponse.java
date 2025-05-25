package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImageResponse {
    private Long id;
    private String imageUrl;
    private ProductDetailResponse productDetail;
    private ColorResponse color;
    private boolean mainColor;
    private boolean mainProduct;
}
