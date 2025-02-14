package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends BaseRepository<Users,Long>{
    Boolean existsByUsername(String userName);
    Optional<Users> findByUsername(String username);
}
