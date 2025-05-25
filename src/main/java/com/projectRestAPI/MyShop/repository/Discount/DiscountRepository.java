package com.projectRestAPI.MyShop.repository.Discount;

import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.repository.BaseRepository;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends BaseRepository<Discount,Long> {
    @EntityGraph(attributePaths = {"discountUsers.users"})
    @NonNull
    List<Discount> findAll();

    @EntityGraph(attributePaths = {"discountUsers.users"})
    @NonNull
    Optional<Discount> findById(@NonNull Long id);
}
