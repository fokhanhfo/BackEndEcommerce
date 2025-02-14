package com.projectRestAPI.MyShop.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PermissionResponse {
    private Long id;
    private String name;
    private String description;
}
