package com.projectRestAPI.MyShop.repository.custom;

import com.projectRestAPI.MyShop.dto.response.BillPage.BillPageResponse;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface BillRepositoryCustom {
    BillPageResponse findBillsByCriteria(Date startDate, Date endDate, String sort, Integer search, Integer status, Pageable pageable);
}
