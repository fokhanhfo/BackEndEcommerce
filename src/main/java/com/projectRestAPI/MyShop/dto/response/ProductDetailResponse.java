package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailResponse {
    private Long id;
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private Integer quantity;
    private ProductShortResponse product;
    private ColorRequest color;
    private SizeRequest size;

    private String imagesUrl;
}
