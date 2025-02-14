package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryRequest {
    private Long id;
    private String name;
    private String description;
}
