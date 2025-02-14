package com.projectRestAPI.MyShop.dto.response;

import com.projectRestAPI.MyShop.model.Category;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductShortResponse {
    private Long id;
    private String name;
    private String detail;
    private CategoryResponse category;
    private Integer status;
}
