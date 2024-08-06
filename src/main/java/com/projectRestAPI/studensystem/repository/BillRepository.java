package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Bill;
import com.projectRestAPI.studensystem.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends BaseRepository<Bill,Long>{

    List<Bill> findByUser(Users users);

    @Modifying
    @Transactional
    @Query("update Bill bill set bill.status = ?1 where bill.id = ?2")
    void updateStatusById(Long BillId, Integer status);

}
