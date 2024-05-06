package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Long> {
    boolean existsByName(String name);
}
