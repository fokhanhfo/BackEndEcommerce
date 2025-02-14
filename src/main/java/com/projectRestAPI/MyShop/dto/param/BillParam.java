package com.projectRestAPI.MyShop.dto.param;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillParam {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer status;
    private String sort;
    private Long search;
}
