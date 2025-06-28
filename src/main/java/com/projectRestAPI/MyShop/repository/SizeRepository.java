package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.SanPham.Size;

public interface SizeRepository extends BaseRepository<Size,Long>{
    Boolean existsByName(String name);
    Boolean existsByNameAndIdNot(String name, Long id);
}
