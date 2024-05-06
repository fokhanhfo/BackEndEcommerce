package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Bill;
import com.projectRestAPI.studensystem.model.BillDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends BaseRepository<BillDetail,Long>{
}
