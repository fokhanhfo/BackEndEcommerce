package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.BestSellingProductDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.QuantityByYearResponse;
import com.projectRestAPI.MyShop.dto.TopSellingProductDTO;
import com.projectRestAPI.MyShop.model.BillDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends BaseRepository<BillDetail,Long>{
    @Query("""
    SELECT new com.projectRestAPI.MyShop.dto.TopSellingProductDTO(
        p.id,
        p.name,
        SUM(bd.quantity)
    )
    FROM BillDetail bd
    JOIN bd.bill b
    JOIN bd.productDetail pd
    JOIN pd.product p
    WHERE b.status = 5
      AND (:month IS NULL OR MONTH(b.createdDate) = :month)
      AND (:year IS NULL OR YEAR(b.createdDate) = :year)
    GROUP BY p.id, p.name
    ORDER BY SUM(bd.quantity) DESC
""")
    List<TopSellingProductDTO> findTopSellingProductsByMonthAndYear(
            @Param("month") Integer month,
            @Param("year") Integer year,
            Pageable pageable
    );

    // lấy số lượng bán được trong các năm
    @Query("""
        SELECT new com.projectRestAPI.MyShop.dto.Dashboard.QuantityByYearResponse(
            YEAR(b.createdDate),
            SUM(bd.quantity)
        )
        FROM BillDetail bd
        JOIN bd.bill b
        WHERE b.status = 5
        GROUP BY YEAR(b.createdDate)
        ORDER BY YEAR(b.createdDate) DESC
    """)
    List<QuantityByYearResponse> getTotalQuantitySoldByYear();




}
