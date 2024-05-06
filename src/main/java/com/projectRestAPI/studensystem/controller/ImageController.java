package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.repository.ImageRepository;
import com.projectRestAPI.studensystem.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

//    @GetMapping("/{product_id}")
//    public ResponseEntity<?> getAll(@PathVariable Long product_id){
//        List<Image> images= imageService.image_product(product_id);
//        if(images.isEmpty()){
//            return new ResponseEntity<>(new ResponseObject("Fail", "Không tìm thấy id ", 1, null), HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.ok(images);
//    }
}
