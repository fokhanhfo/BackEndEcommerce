package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.UserImage;

import java.util.Optional;

public interface UserImageRepository extends BaseRepository<UserImage,Long>{
    Optional<UserImage> findByName(String name);
}
