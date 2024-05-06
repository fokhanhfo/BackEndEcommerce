package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.InvalidatedToken;

public interface InvalidatedTokenRepository extends BaseRepository<InvalidatedToken,Long>{
    Boolean existsByIdCode(String IdCode);
}
