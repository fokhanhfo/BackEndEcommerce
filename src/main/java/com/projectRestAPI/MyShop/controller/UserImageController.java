package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.ImageRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.ImageService;
import com.projectRestAPI.MyShop.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/userImage")
public class UserImageController {
    @Autowired
    private UserImageService userImageService;

    @GetMapping("/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename) throws IOException {
        return userImageService.getImage(filename);
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseObject> deleteImage(@PathVariable Long id){
//        return imageService.deleteImage(id);
//    }

}
