package com.projectRestAPI.MyShop.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDetailMappingRequest {
    private Long id;
    private BigDecimal importPrice;
    private BigDecimal sellingPrice;
    private String product; // JSON string
    private String color;   // JSON string
    private MultipartFile[] image;

}
