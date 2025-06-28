package com.projectRestAPI.MyShop.service.Impl.Product;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;
import com.projectRestAPI.MyShop.repository.ProductDetailSizeRepository;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductDetailSizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailSizeServiceImpl extends BaseServiceImpl<ProductDetailSize, Long , ProductDetailSizeRepository> implements ProductDetailSizeService {
    @Override
    public ResponseEntity<ResponseObject> deleteProductDetailSize(Long id) {
        Optional<ProductDetailSize> otp = findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, null), HttpStatus.OK);
    }
}
