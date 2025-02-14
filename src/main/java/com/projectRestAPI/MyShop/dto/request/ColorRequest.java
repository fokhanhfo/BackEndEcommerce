package com.projectRestAPI.MyShop.dto.request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ColorRequest {
    private Long id;
    private String name;
    private String key;
    private Integer status;
}
