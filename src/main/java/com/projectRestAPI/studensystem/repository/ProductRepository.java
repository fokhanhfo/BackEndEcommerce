package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Product;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long> {
    @Query("SELECT p.quantity FROM Product p WHERE p.id = ?1")
    Integer findQuantityById(Long productId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findProductByCategory(Pageable pageable, @Param("categoryId") Long categoryId);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = ?2 WHERE p.id = ?1")
    void updateQuantityById(Long productId, Integer newQuantity);
}
