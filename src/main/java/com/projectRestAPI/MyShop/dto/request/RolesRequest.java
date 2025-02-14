package com.projectRestAPI.MyShop.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RolesRequest {
    private Long id;
    private String name;
    private List<PermissionRequest> permissions;
}
