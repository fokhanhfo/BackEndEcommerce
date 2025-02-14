package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<Permission,Long>{
    boolean existsByName(String name);
}
