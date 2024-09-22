package com.projectRestAPI.studensystem.repository.custom.RepositoryCustomImpl;

import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findProductsByCriteria(Long categoryId, BigDecimal priceGte, BigDecimal priceLte, String sort, List<String> searchKeywords, Integer status,Pageable pageable) {
        StringBuilder queryStr = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        if (categoryId != null) {
            queryStr.append(" AND p.category.id = :categoryId");
        }
        if (priceGte != null) {
            queryStr.append(" AND p.price >= :priceGte");
        }
        if (priceLte != null) {
            queryStr.append(" AND p.price <= :priceLte");
        }
        if(status != null){
            queryStr.append(" AND p.status = :status");
        }
        if (!searchKeywords.isEmpty()) {
            String searchCondition = searchKeywords.stream()
                    .map(keyword -> "p.name LIKE :keyword" + searchKeywords.indexOf(keyword))
                    .collect(Collectors.joining(" AND "));
            queryStr.append(" AND (").append(searchCondition).append(")");
        }
        if (sort != null && !sort.isEmpty()) {
            queryStr.append(" ORDER BY ");
            if ("Price:ASC".equals(sort)) {
                queryStr.append("p.price ASC");
            } else if ("Price:DESC".equals(sort)) {
                queryStr.append("p.price DESC");
            }
        }

        TypedQuery<Product> query = entityManager.createQuery(queryStr.toString(), Product.class);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (priceGte != null) {
            query.setParameter("priceGte", priceGte);
        }
        if (priceLte != null) {
            query.setParameter("priceLte", priceLte);
        }

        query.setParameter("status",status);

        for (int i = 0; i < searchKeywords.size(); i++) {
            query.setParameter("keyword" + i, "%" + searchKeywords.get(i) + "%");
        }

        List<Product> products = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = query.getResultStream().count();

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Integer findCountProduct(Long categoryId, BigDecimal priceGte, BigDecimal priceLte, String sort, List<String> searchKeywords, Integer status) {
        StringBuilder queryStr = new StringBuilder("SELECT count(p.id) FROM Product p WHERE 1=1");
        if (categoryId != null) {
            queryStr.append(" AND p.category.id = :categoryId");
        }
        if (priceGte != null) {
            queryStr.append(" AND p.price >= :priceGte");
        }
        if (priceLte != null) {
            queryStr.append(" AND p.price <= :priceLte");
        }
        queryStr.append(" AND p.status = :status");
        if (!searchKeywords.isEmpty()) {
            String searchCondition = searchKeywords.stream()
                    .map(keyword -> "p.name LIKE :keyword" + searchKeywords.indexOf(keyword))
                    .collect(Collectors.joining(" AND "));
            queryStr.append(" AND (").append(searchCondition).append(")");
        }
        if (sort != null && !sort.isEmpty()) {
            queryStr.append(" ORDER BY ");
            if ("Price:ASC".equals(sort)) {
                queryStr.append("p.price ASC");
            } else if ("Price:DESC".equals(sort)) {
                queryStr.append("p.price DESC");
            }
        }

        TypedQuery<Long> query = entityManager.createQuery(queryStr.toString(), Long.class);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (priceGte != null) {
            query.setParameter("priceGte", priceGte);
        }
        if (priceLte != null) {
            query.setParameter("priceLte", priceLte);
        }

        if(status != null) {
            query.setParameter("status", status);
        }

        for (int i = 0; i < searchKeywords.size(); i++) {
            query.setParameter("keyword" + i, "%" + searchKeywords.get(i) + "%");
        }

        return query.getSingleResult().intValue();
    }
}
