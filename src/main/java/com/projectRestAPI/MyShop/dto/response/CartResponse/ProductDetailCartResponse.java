package com.projectRestAPI.MyShop.dto.response.CartResponse;

import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.response.ColorResponse;
import com.projectRestAPI.MyShop.dto.response.ImageResponse;
import com.projectRestAPI.MyShop.dto.response.ProductDetailSizeResponse;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailCartResponse {
    private Long id;
    private ProductCartResponse product;
    private ColorResponse color;
    private List<ImageResponse> image;
    private List<ProductDetailSizeResponse> productDetailSizes;
}
