package com.projectRestAPI.MyShop.repository.Discount;

import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.model.Discount.DiscountUser;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountUserRepository extends BaseRepository<DiscountUser,Long> {
    @Query("SELECT du.users.id FROM DiscountUser du WHERE du.discount.id = :discountId")
    List<Long> findUserIdsByDiscountId(@Param("discountId") Long discountId);

    @Query("SELECT du FROM DiscountUser du " +
            "WHERE du.users.id = :userId " +
            "AND (:isUsed IS NULL OR du.isUsed = :isUsed) " +
            "AND du.discount.status = 1 " + // chỉ lấy discount còn hiệu lực (status = 1)
            "AND du.discount.startTime <= CURRENT_TIMESTAMP " + // đã bắt đầu
            "AND du.discount.endTime >= CURRENT_TIMESTAMP")     // chưa kết thúc
    List<DiscountUser> findDiscountUserByUserId(@Param("userId") Long userId, @Param("isUsed") Boolean isUsed);


    DiscountUser findByDiscountAndUsers(Discount discount, Users users);

}
