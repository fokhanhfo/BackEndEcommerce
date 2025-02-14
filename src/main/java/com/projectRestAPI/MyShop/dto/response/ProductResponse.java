package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.dto.request.ProductDetailRequest;
import com.projectRestAPI.MyShop.model.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductResponse {
    private Long id;
    private String name;
    private String detail;
    private CategoryResponse category;
    private Integer status;
    private List<ProductDetailResponse> productDetails;
}
