package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;

import java.util.Optional;

public interface ProductDetailSizeRepository extends BaseRepository<ProductDetailSize,Long>{
    Optional<ProductDetailSize> findByProductDetailIdAndSizeId(Long productDetailId, Long sizeId);
}
