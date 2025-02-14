package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryResponse {
    private String name;
    private String description;
}
