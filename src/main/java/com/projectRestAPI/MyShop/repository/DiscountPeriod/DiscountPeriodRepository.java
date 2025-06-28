package com.projectRestAPI.MyShop.repository.DiscountPeriod;


import com.projectRestAPI.MyShop.model.DiscountPeriod.DiscountPeriod;
import com.projectRestAPI.MyShop.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountPeriodRepository extends BaseRepository<DiscountPeriod,Long> {
    Boolean existsByDiscountPeriodCode (String existsByDiscountPeriodCode);

    boolean existsByDiscountPeriodCodeAndIdNot(String code, Long id);

}
