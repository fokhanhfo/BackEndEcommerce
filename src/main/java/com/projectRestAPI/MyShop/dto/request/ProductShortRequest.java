package com.projectRestAPI.MyShop.dto.request;

import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductShortRequest {
    private Long id;
    private String name;
    private String detail;
    private CategoryResponse category;
    private Integer status;
}
