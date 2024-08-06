package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
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
