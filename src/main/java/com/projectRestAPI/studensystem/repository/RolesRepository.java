package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends BaseRepository<Roles,Long>{
    List<Roles> findByNameIn (List<String> name);
}
