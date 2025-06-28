package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends BaseRepository<ProductDetail,Long>{
    @Query("select pd from ProductDetail pd join pd.productDetailSizes pds where pd.color.id = :colorId AND pds.size.id = :sizeId ")
    Optional<ProductDetail> findByColorIdAndSizeId(@Param("colorId") Long colorId,
                                                   @Param("sizeId") Long sizeId);
}
