package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailSizeRepository extends BaseRepository<ProductDetailSize,Long>{
    Optional<ProductDetailSize> findByProductDetailIdAndSizeId(Long productDetailId, Long sizeId);
}
