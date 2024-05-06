package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends BaseRepository<Users,Long>{
    Boolean existsByUsername(String userName);
    Optional<Users> findByUsername(String username);
}
