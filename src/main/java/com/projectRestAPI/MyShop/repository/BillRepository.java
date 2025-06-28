package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyOrderCount;
import com.projectRestAPI.MyShop.dto.BillStatistics.MonthlyRevenue;
import com.projectRestAPI.MyShop.dto.BillStatistics.OrderStatusRatio;
import com.projectRestAPI.MyShop.dto.BillStatistics.PaymentMethodRevenue;
import com.projectRestAPI.MyShop.dto.Dashboard.BillStatusCountDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.RevenueBySizeDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.RevenueResponse;
import com.projectRestAPI.MyShop.dto.response.RevenueByCategoryDTO;
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

    @Query("SELECT MONTH(b.createdDate) as month, SUM(b.total_price - b.discountUser + (30000 - b.discountShip)) as revenue " +
            "FROM Bill b " +
            "WHERE YEAR(b.createdDate) = YEAR(CURRENT_DATE) AND b.status = 5 " +
            "GROUP BY MONTH(b.createdDate) " +
            "ORDER BY MONTH(b.createdDate)")
    List<MonthlyRevenue> getMonthlyRevenueCurrentYear();

    @Query("SELECT MONTH(b.createdDate) as month, COUNT(b.id) as orderCount " +
            "FROM Bill b " +
            "WHERE YEAR(b.createdDate) = YEAR(CURRENT_DATE) AND b.status = 5 " +
            "GROUP BY MONTH(b.createdDate)")
    List<MonthlyOrderCount> getMonthlyOrderCount();

    @Query("SELECT b.payMethod as payMethod, SUM(b.total_price - b.discountUser + (30000 - b.discountShip)) as totalRevenue " +
            "FROM Bill b " +
            "WHERE b.status = 5 AND YEAR(b.createdDate) = YEAR(CURRENT_DATE) " +
            "GROUP BY b.payMethod")
    List<PaymentMethodRevenue> getRevenueByPaymentMethod();

    @Query("SELECT b.status as status, COUNT(b.id) as count " +
            "FROM Bill b WHERE YEAR(b.createdDate) = YEAR(CURRENT_DATE) " +
            "GROUP BY b.status")
    List<OrderStatusRatio> getOrderStatusRatio();


    //Doanh thu theo tháng
    @Query("SELECT b FROM Bill b WHERE YEAR(b.createdDate) = :year AND b.status = 5")
    List<Bill> findAllCompletedByYear(@Param("year") int year);

    //doanh thu theo doanh mục
    @Query("""
    SELECT new com.projectRestAPI.MyShop.dto.response.RevenueByCategoryDTO(
        c.name, YEAR(b.createdDate), 
        SUM(b.total_price - b.discountUser + (30000 - b.discountShip))
    )
    FROM Bill b
    JOIN b.billDetail bd
    JOIN bd.productDetail pd
    JOIN pd.product p
    JOIN p.category c
    WHERE b.status = 5 AND YEAR(b.createdDate) = :year
    GROUP BY c.name, YEAR(b.createdDate)
""")
    List<RevenueByCategoryDTO> getRevenueByCategoryAndYear(@Param("year") int year);


    @Query("""
        SELECT new com.projectRestAPI.MyShop.dto.Dashboard.RevenueResponse(
            YEAR(b.createdDate),
            SUM(b.total_price - b.discountUser + (30000 - b.discountShip))
        )
        FROM Bill b
        WHERE b.status = 5
        GROUP BY YEAR(b.createdDate)
        ORDER BY YEAR(b.createdDate) DESC
    """)
    List<RevenueResponse> getRevenuePerYear();


    @Query(value = """
        SELECT 
            'Đang xử lý' AS label, COUNT(*) AS total
        FROM bill 
        WHERE status IN (0, 1)
        UNION ALL
        SELECT 
            'Đang giao hàng', COUNT(*) 
        FROM bill 
        WHERE status IN (2, 3)
        UNION ALL
        SELECT 
            'Hoàn thành', COUNT(*) 
        FROM bill 
        WHERE status = 5
        UNION ALL
        SELECT 
            'Đã hủy', COUNT(*) 
        FROM bill 
        WHERE status = 6
        UNION ALL
        SELECT 
            'Hoàn tiền', COUNT(*) 
        FROM bill 
        WHERE status = 7
    """, nativeQuery = true)
    List<BillStatusCountDTO> getBillStatusCounts();





}
