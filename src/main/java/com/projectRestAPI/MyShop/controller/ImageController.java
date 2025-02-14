package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.ImageRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/product/{id}")
    public ResponseEntity<ResponseObject> addImages(@PathVariable Long id , @ModelAttribute ImageRequest imageRequest){
        return imageService.addImage(id,imageRequest.getFile());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename) throws IOException {
        return imageService.getImage(filename);
    }

    @GetMapping("/product/{id}")
    public  ResponseEntity<?> getImageAll(@PathVariable Long id){
        return imageService.getAllImagesByProductId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateImageProduct(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        return imageService.updateImageProduct(id,file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteImage(@PathVariable Long id){
        return imageService.deleteImage(id);
    }

}
