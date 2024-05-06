package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;

import java.util.List;

public interface ImageService extends BaseService<Image,Long>{
//    public List<Image> image_product(Long id);
    public List<Image> findAllById(ImageRequest imageRequest);
    public List<Image> saveImages(List<Image> images);
}
