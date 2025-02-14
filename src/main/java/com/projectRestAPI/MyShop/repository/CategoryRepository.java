package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name,Long id);
}
