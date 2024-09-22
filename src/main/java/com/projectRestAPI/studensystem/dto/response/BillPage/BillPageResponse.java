package com.projectRestAPI.studensystem.dto.response.BillPage;

import com.projectRestAPI.studensystem.model.Bill;
import lombok.*;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillPageResponse {
    private Page<Bill> billPage;
    private long totalRows;
}
