package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.Dashboard.InventoryValueDTO;
import com.projectRestAPI.MyShop.dto.Dashboard.ProductInventoryDTO;
import com.projectRestAPI.MyShop.dto.ProductStatistics.*;
import com.projectRestAPI.MyShop.model.SanPham.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Long id);

//    @Query("SELECT p FROM Product p " +
//            "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
//            "AND (:priceGte IS NULL OR p.price >= :priceGte) " +
//            "AND (:priceLte IS NULL OR p.price <= :priceLte) " +
//            "AND (:search IS NULL OR p.name like %:search%)"+
//            "ORDER BY " +
//            "  CASE WHEN :sort = 'Price:ASC' THEN p.price END ASC, " +
//            "  CASE WHEN :sort = 'Price:DESC' THEN p.price END DESC")
//    Page<Product> findProducts(@Param("categoryId") Long categoryId,
//                               @Param("priceGte") BigDecimal priceGte,
//                               @Param("priceLte") BigDecimal priceLte,
//                               @Param("sort") String sort,
//                               @Param("search") String search,
//                               Pageable pageable);

    @Query("SELECT p from Product p ORDER BY p.id DESC")
    Page<Product> findProductNew(Pageable pageable);

    @Query("select count(p.id) from Product p ")
    Integer getCount();

    @Query(value = """
        SELECT 
            CASE 
                WHEN p.status = 1 THEN 'Đang hoạt động'
                WHEN p.status = 0 THEN 'Ngừng bán'
                WHEN p.status = 2 THEN 'Hết hàng'
                ELSE 'Không xác định'
            END AS status,
            COUNT(*) AS count
        FROM Product p
        GROUP BY p.status
    """, nativeQuery = true)
    List<ProductStatusDTO> getProductCountByStatusNative();


    @Query("""
        SELECT 
            p.id AS id,
            p.name AS name,
            p.importPrice AS importPrice,
            p.sellingPrice AS sellingPrice,
            SUM(pds.quantity) AS totalQuantity
        FROM Product p
        JOIN p.productDetails pd
        JOIN pd.productDetailSizes pds
        GROUP BY p.id, p.name, p.importPrice, p.sellingPrice
        ORDER BY SUM(pds.quantity) DESC
    """)
    List<ProductQuantityView> findTopProductWithMostQuantity(Pageable pageable);



    @Query(value = """
        SELECT p.id AS id,
               p.name AS name,
               p.import_price AS importPrice,
               p.selling_price AS sellingPrice,
               COALESCE(SUM(pds.quantity), 0) AS totalQuantity
        FROM product p
        LEFT JOIN product_detail pd ON p.id = pd.product_id
        LEFT JOIN product_detail_size pds ON pd.id = pds.product_detail_id
        GROUP BY p.id, p.name, p.import_price, p.selling_price
        HAVING COALESCE(SUM(pds.quantity), 0) < 10
    """, nativeQuery = true)
    List<ProductQuantityDTO> findProductWithTotalQuantityLessThanTenNative();

    @Query(value = """
        SELECT p.id AS id,
               p.name AS name,
               p.import_price AS importPrice,
               p.selling_price AS sellingPrice,
               SUM(bd.quantity) AS totalSold
        FROM bill_detail bd
        JOIN product_detail pd ON bd.product_detail_id = pd.id
        JOIN product p ON pd.product_id = p.id
        JOIN bill b on b.id = bd.bill_id
        where b.status = 5
        GROUP BY p.id, p.name, p.import_price, p.selling_price
        ORDER BY totalSold DESC
        LIMIT 1
    """, nativeQuery = true)
    ProductSalesView findBestSellingProduct();

    @Query("""
        SELECT p.id AS id, p.name AS name,p.sellingPrice AS selling, p.importPrice AS import, SUM(pds.quantity) AS quantity
        FROM Product p
        JOIN p.productDetails pd
        JOIN pd.productDetailSizes pds
        GROUP BY p.id, p.name
    """)
    List<ProductInventoryDTO> getInventoryByProduct();









//    @Modifying
//    @Transactional
//    @Query("UPDATE Product p SET p.quantity = ?2 WHERE p.id = ?1")
//    void updateQuantityById(Long productId, Integer newQuantity);
}
