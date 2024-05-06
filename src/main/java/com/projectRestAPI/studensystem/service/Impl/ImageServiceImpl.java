package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.ImageRepository;
import com.projectRestAPI.studensystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends BaseServiceImpl<Image,Long, ImageRepository> implements ImageService {


    @Override
    public List<Image> findAllById(ImageRequest imageRequest) {
        return null;
    }

    @Override
    public List<Image> saveImages(List<Image> images) {
        return repository.saveAll(images);
    }
}
