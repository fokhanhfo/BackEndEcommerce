package com.projectRestAPI.MyShop.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projectRestAPI.MyShop.dto.response.ProductDetailSizeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailRequest {
    private Long id;
    private ProductShortRequest product;

    private ColorRequest color;
    private Long isMainId;
    private Long isMainNew;

    private String isMainIdNew;

    @JsonIgnore
    private List<MultipartFile> images;
    private List<ProductDetailSizeRequest> productDetailSizes;
}
