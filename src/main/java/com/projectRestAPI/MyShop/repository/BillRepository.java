package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Bill;
import com.projectRestAPI.MyShop.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends BaseRepository<Bill,Long> {

    List<Bill> findByUser(Users users);

    @Query("SELECT b FROM Bill b WHERE " +
            "(:startDate IS NULL OR b.createdDate >= :startDate) AND " +
            "(:endDate IS NULL OR b.createdDate <= :endDate) AND " +
            "(:status IS NULL OR b.status = :status) AND " +
            "(:search IS NULL OR CAST(b.id AS string) LIKE %:search% OR b.phone LIKE %:search% OR b.email LIKE %:search% OR b.address LIKE %:search%) AND " +
            "(:user_id IS NULL OR b.user.id = :user_id)")
    Page<Bill> findBillsByCriteria(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("search") Long search,
            @Param("status") Integer status,
            @Param("user_id") Long id,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("update Bill bill set bill.status = ?1 where bill.id = ?2")
    void updateStatusById(Long BillId, Integer status);

}
