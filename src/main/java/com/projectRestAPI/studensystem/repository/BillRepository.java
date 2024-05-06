package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends BaseRepository<Bill,Long>{
    @Modifying
    @Transactional
    @Query("update Bill bill set bill.status = ?1 where bill.id = ?2")
    void updateStatusById(Long BillId, int status);

}
