package com.projectRestAPI.MyShop.repository.custom;

import com.projectRestAPI.MyShop.model.SanPham.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> findProductsByCriteria(Long categoryId, BigDecimal priceGte, BigDecimal priceLte, String sort, List<String> searchKeywords, Integer status ,Pageable pageable);

    Integer findCountProduct(Long categoryId, BigDecimal priceGte, BigDecimal priceLte, String sort, List<String> searchKeywords, Integer status);
}
