package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.SanPham.Color;

public interface ColorRepository extends BaseRepository<Color,Long>{
    Boolean existsByName(String name);

    Boolean existsByKey(String key);

    Boolean existsByNameAndIdNot(String name, Long id);
    Boolean existsByKeyAndIdNot(String key, Long id);

}
