package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.request.ImageRequest;
import com.projectRestAPI.studensystem.dto.response.ImageResponse;
import com.projectRestAPI.studensystem.dto.response.ImageResponseAll;
import com.projectRestAPI.studensystem.dto.response.ProductResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.ImageRepository;
import com.projectRestAPI.studensystem.service.ImageService;
import com.projectRestAPI.studensystem.service.ProductService;
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
public class ImageServiceImpl extends BaseServiceImpl<Image,Long, ImageRepository> implements ImageService {

    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Override
    public ResponseEntity<ResponseObject> addImage(Long idProduct, List<MultipartFile> files) {
        Product product = productService.findById(idProduct).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (files != null && !files.isEmpty()) {
            List<Image> images = new ArrayList<>();

            for (MultipartFile file : files) {
                String fileType = file.getContentType();
                if (productServiceImpl.isValidImageOrVideo(fileType)) {
                    try {
                        String fileName = productServiceImpl.writeFile(file);
                        Image image = Image.builder()
                                .name(fileName)
                                .file(file.getBytes())
                                .type(fileType)
                                .product(product)
                                .build();

                        images.add(image);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.READ_WRITE_ERROR);
                    }
                } else {
                    return new ResponseEntity<>(new ResponseObject("Error", "File không đúng định dạng", 400, null), HttpStatus.BAD_REQUEST);
                }
            }
            repository.saveAll(images);
            List<ImageResponse> imageResponse = images.stream().map(this::convertToImageResponse).collect(Collectors.toList());

            return new ResponseEntity<>(new ResponseObject("Success", "Thêm ảnh thành công", 200, imageResponse), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject("Error", "Danh sách tệp rỗng", 400, null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getImage(String name) throws IOException {
        Optional<Image> imageOptional = repository.findByName(name);
        if (imageOptional.isEmpty()){
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        Image image = imageOptional.get();
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

    @Override
    public ResponseEntity<ResponseObject> getAllImagesByProductId(Long productId){
        List<Image> images = repository.findByProductId(productId);
        if (images.isEmpty()) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        List<ImageResponse> imageResponse = images.stream().map(this::convertToImageResponse).collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",1,imageResponse),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> updateImageProduct(Long imageId,MultipartFile file){
        Optional<Image> imageOptional = findById(imageId);
        if (imageOptional.isEmpty()){
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        Image image = imageOptional.get();

        if(!file.isEmpty()){
            String fileType = file.getContentType();
            if(productServiceImpl.isValidImageOrVideo(fileType)){
                try {
                    String oldFileName = image.getName();
                    Path oldFilePath = Paths.get(UPLOAD_DIR,oldFileName);
                    Files.deleteIfExists(oldFilePath);

                    String fileName = productServiceImpl.writeFile(file);
                    image.setName(fileName);
                    image.setFile(file.getBytes());
                    image.setType(fileType);
                    image.setProduct(image.getProduct());
                    createNew(image);
                    return new ResponseEntity<>(new ResponseObject("Success", "Update ảnh thành công", 200, null), HttpStatus.OK);
                }catch (IOException e){
                    throw new AppException(ErrorCode.READ_WRITE_ERROR);
                }
            }
        }
        throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteImage(Long id) {
        Optional<Image> imageOptional = findById(id);
        if (imageOptional.isEmpty()){
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("Succes", "Xóa ảnh thành công", 200, null), HttpStatus.OK);
    }

    public ImageResponse convertToImageResponse(Image image) {
        return new ImageResponse(image.getId(), "http://localhost:8080/image/" + image.getName());
    }

}
