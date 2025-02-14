package com.projectRestAPI.MyShop.dto.request;

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
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private Integer quantity;
    private ProductRequest product;

    private ColorRequest color;
    private SizeRequest size;

    private MultipartFile image;
}
