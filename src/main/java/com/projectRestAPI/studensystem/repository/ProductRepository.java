package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Product;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long> {
    Boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:priceGte IS NULL OR p.price >= :priceGte) " +
            "AND (:priceLte IS NULL OR p.price <= :priceLte) " +
            "AND (:search IS NULL OR p.name like %:search%)"+
            "ORDER BY " +
            "  CASE WHEN :sort = 'Price:ASC' THEN p.price END ASC, " +
            "  CASE WHEN :sort = 'Price:DESC' THEN p.price END DESC")
    Page<Product> findProducts(@Param("categoryId") Long categoryId,
                               @Param("priceGte") BigDecimal priceGte,
                               @Param("priceLte") BigDecimal priceLte,
                               @Param("sort") String sort,
                               @Param("search") String search,
                               Pageable pageable);

    @Query("SELECT p from Product p ORDER BY p.id DESC")
    Page<Product> findProductNew(Pageable pageable);

    @Query("select count(p.id) from Product p ")
    Integer getCount();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = ?2 WHERE p.id = ?1")
    void updateQuantityById(Long productId, Integer newQuantity);
}
