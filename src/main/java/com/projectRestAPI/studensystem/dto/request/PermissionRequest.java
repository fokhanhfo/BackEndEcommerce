package com.projectRestAPI.studensystem.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PermissionRequest {
    private Long id;
    private String name;
    private String description;
}
