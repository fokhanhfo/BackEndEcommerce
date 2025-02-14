package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService extends BaseService<Image,Long>{

    ResponseEntity<ResponseObject> addImage(Long idProduct, List<MultipartFile> files);
    ResponseEntity<?>  getImage(String name) throws IOException;

    ResponseEntity<ResponseObject> getAllImagesByProductId(Long productId);

    ResponseEntity<ResponseObject> updateImageProduct(Long imageId,MultipartFile file);

    ResponseEntity<ResponseObject> deleteImage(Long id);
}
