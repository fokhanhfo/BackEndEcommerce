package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.CategoryQuantitySummary;
import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import com.projectRestAPI.MyShop.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Long> {
    @Query(value = "SELECT c.id AS id, c.name AS name, c.description, COALESCE(SUM(pds.quantity), 0) AS totalQuantity " +
            "FROM category c " +
            "LEFT JOIN product p ON c.id = p.category_id " +
            "LEFT JOIN product_detail pd ON p.id = pd.product_id " +
            "LEFT JOIN product_detail_size pds ON pd.id = pds.product_detail_id " +
            "GROUP BY c.id, c.name",
            nativeQuery = true)
    List<CategoryQuantitySummary> getCategoryQuantitySummary();


    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name,Long id);
}
