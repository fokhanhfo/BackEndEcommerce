package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Image;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends BaseRepository<Image,Long> {
    List<Image> findByProductId(Long id);

    List<Image> findByProductDetailId(Long id);

    Optional<Image> findByName(String name);
}
