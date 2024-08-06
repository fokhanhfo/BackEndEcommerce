package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.repository.ImageRepository;
import com.projectRestAPI.studensystem.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
