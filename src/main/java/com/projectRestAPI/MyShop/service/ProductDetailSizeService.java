package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;
import org.springframework.http.ResponseEntity;

public interface ProductDetailSizeService extends BaseService<ProductDetailSize,Long>{
    ResponseEntity<ResponseObject> deleteProductDetailSize(Long id);
}
