package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CategoryRequest {
    private String name;
    private String description;
}
