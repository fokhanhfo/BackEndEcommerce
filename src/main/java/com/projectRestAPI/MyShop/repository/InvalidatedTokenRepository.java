package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.InvalidatedToken;

public interface InvalidatedTokenRepository extends BaseRepository<InvalidatedToken,Long>{
    Boolean existsByIdCode(String IdCode);
}
