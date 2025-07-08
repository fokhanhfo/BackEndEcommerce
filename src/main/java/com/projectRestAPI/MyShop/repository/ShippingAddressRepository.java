package com.projectRestAPI.MyShop.repository;


import com.projectRestAPI.MyShop.model.ShippingAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends BaseRepository<ShippingAddress,Long>{
    List<ShippingAddress> findByUserId(Long userId);

    long countByUserId(Long userId);

    @Modifying
    @Query("UPDATE ShippingAddress s SET s.isDefault = false WHERE s.user.id = :userId")
    void resetDefaultForUser(Long userId);



}
