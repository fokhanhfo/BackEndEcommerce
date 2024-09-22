package com.projectRestAPI.studensystem.dto.request;

import com.projectRestAPI.studensystem.model.Category;
import com.projectRestAPI.studensystem.model.Image;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ProductRequest {
    private Long id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotBlank(message = "Chi tiết sản phẩm không được để trống")
    private String detail;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(1)
    private BigDecimal price;
    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(1)
    private Integer quantity;
    @NotNull(message = "Danh mục sản phẩm không được để trống")
    private Long category;
    private Integer status;
    private List<MultipartFile> images;
}
