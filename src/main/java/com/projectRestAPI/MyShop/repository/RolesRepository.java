package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends BaseRepository<Roles,Long>{

    Roles findByName(String name);
    List<Roles> findByNameIn (List<String> name);
}
