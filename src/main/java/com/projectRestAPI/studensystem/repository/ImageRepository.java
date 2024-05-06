package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.service.BaseService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends BaseRepository<Image,Long> {
//    @Query("SELECT img FROM Image img WHERE img.image_product_id = :product_id")
//    List<Image> findImageByProduct_id(@Param("product_id") Long productId);
}
