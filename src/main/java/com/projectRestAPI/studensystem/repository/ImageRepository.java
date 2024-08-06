package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.service.BaseService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends BaseRepository<Image,Long> {
    List<Image> findByProductId(Long id);

    Optional<Image> findByName(String name);
}
