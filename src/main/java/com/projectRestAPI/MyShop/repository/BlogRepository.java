package com.projectRestAPI.MyShop.repository;


import com.projectRestAPI.MyShop.model.Blog;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends BaseRepository<Blog,Long> {
    boolean existsByTitle(String title);

}
