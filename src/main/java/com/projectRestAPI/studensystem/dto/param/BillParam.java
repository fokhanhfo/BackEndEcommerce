package com.projectRestAPI.studensystem.dto.param;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillParam {
    private Date startDate;
    private Date endDate;
    private Integer status;
    private String sort;
    private Integer search;
}
