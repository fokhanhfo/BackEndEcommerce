package com.projectRestAPI.MyShop.repository.DiscountPeriod;

import com.projectRestAPI.MyShop.model.DiscountPeriod.ProductDiscountPeriod;
import com.projectRestAPI.MyShop.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDiscountPeriodRepository extends BaseRepository<ProductDiscountPeriod,Long> {
    @Query("SELECT du.product.id FROM ProductDiscountPeriod du WHERE du.discountPeriod.id = :discountPeriodId")
    List<Long> findProductIdsByDiscountId(@Param("discountPeriodId") Long discountPeriodId);
}
