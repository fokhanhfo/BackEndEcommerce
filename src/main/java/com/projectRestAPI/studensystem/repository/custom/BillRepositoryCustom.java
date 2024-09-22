package com.projectRestAPI.studensystem.repository.custom;

import com.projectRestAPI.studensystem.dto.response.BillPage.BillPageResponse;
import com.projectRestAPI.studensystem.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface BillRepositoryCustom {
    BillPageResponse findBillsByCriteria(Date startDate, Date endDate, String sort, Integer search, Integer status, Pageable pageable);
}
