package com.projectRestAPI.MyShop.dto.request;

import jakarta.validation.constraints.*;
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
public class ProductRequest {
    private Long id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotBlank(message = "Chi tiết sản phẩm không được để trống")
    private String detail;
    @NotNull(message = "Danh mục sản phẩm không được để trống")
    private CategoryRequest category;
    private Integer status;
    // private List<MultipartFile> images;
    private List<ProductDetailRequest> productDetails;
}
