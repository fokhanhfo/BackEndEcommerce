package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart,Long>{
    @Query("SELECT cart FROM Cart cart WHERE cart.user.id = :userId")
    List<Cart> findCartByUser(Long userId);

    @Query("SELECT cart FROM Cart cart WHERE cart.productDetail.id = :productId AND cart.user.id = :userId")
    Optional<Cart> findCartProduct(@Param("productId") Long productId, @Param("userId") Long userId);


    @Modifying
    @Query("DELETE FROM Cart cart WHERE cart.user.id = :userId")
    void deleteByUser(Long userId);
}
