package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.response.ImageResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.ColorMapper;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.UserImage;
import com.projectRestAPI.MyShop.repository.ImageRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailRepository;
import com.projectRestAPI.MyShop.repository.UserImageRepository;
import com.projectRestAPI.MyShop.service.ImageService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.Impl.Product.ProductServiceImpl;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.service.UserImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImageServiceImpl extends BaseServiceImpl<UserImage,Long, UserImageRepository> implements UserImageService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public ResponseEntity<?> getImage(String name) throws IOException {
        Optional<UserImage> imageOptional = repository.findByName(name);
        if (imageOptional.isEmpty()){
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        UserImage image = imageOptional.get();
        String filePath = UPLOAD_DIR + image.getName();
        byte[] imageData = Files.readAllBytes(new File(filePath).toPath());

        if(image.getType().startsWith("image/")){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(image.getType())).body(imageData);
        }
        if(image.getType().startsWith("video/")){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(image.getType())).body(imageData);
        }
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Unsupported file type");
    }

}
