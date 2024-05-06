package com.projectRestAPI.studensystem.dto.request;

import com.projectRestAPI.studensystem.model.Permission;
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
    private List<Long> permissions;
}
