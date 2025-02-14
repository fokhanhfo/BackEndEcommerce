package com.projectRestAPI.MyShop.service.Impl.Product;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.ProductDetailRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ProductDetailResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.ProductDetailMapper;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.repository.ImageRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailRepository;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail,Long, ProductDetailRepository> implements ProductDetailService {
    @Autowired
    private ProductDetailMapper productDetailMapper;
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ImageRepository imageRepository;
    @Override
    public ResponseEntity<ResponseObject> add(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailMapper.toProductDetail(productDetailRequest);
        Image image = saveImageFile(productDetailRequest.getImage(), productDetail);
        imageRepository.save(image);
        productDetail.setImage(image);
//        Category category = categoryMapper.toCategory(productRequest.getCategory());
//        category.setId(productRequest.getCategory().getId());
//        product.setCategory(category);
        repository.save(productDetail);
        ProductDetailResponse productDetailResponse = productDetailMapper.toProductDetailResponse(productDetail);
        return new ResponseEntity<>(new ResponseObject("Succes","Thêm sản phẩm thành công",200,productDetailResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<ProductDetail> productDetailOptional = repository.findById(id);
        if(productDetailOptional.isEmpty()){
            throw new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
        }
        ProductDetailResponse productDetailResponse = productDetailMapper.toProductDetailResponse(productDetailOptional.get());
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy sa phẩm thành công",200,productDetailResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(ProductDetailRequest productDetailRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }

//    private Image saveImageFile(ImageColorRequest imageColorRequest, ProductDetail productDetail){
//        MultipartFile file = imageColorRequest.getImages();
//        if(file != null && !file.isEmpty()){
//            String fileType = file.getContentType();
//            if(isValidImageOrVideo(fileType)){
//                try {
//                    String fileName = writeFile(file);
//                    Image image = Image.builder()
//                            .name(fileName)
//                            .file(file.getBytes())
//                            .type(fileType)
//                            .productDetail(productDetail)
//                            .build();
//                    return image;
//                }catch (IOException e){
//                    throw new AppException(ErrorCode.READ_WRITE_ERROR);
//                }
//            }
//        }else {
//            throw new RuntimeException("Invalid file type. Only image and video files are allowed.");
//        }
//        return null;
//    }

    private Image saveImageFile(MultipartFile file, ProductDetail productDetail){
        if(file != null && !file.isEmpty()){
            String fileType = file.getContentType();
            if(isValidImageOrVideo(fileType)){
                try {
                    String fileName = writeFile(file);
                    Image image = Image.builder()
                            .name(fileName)
                            .file(file.getBytes())
                            .type(fileType)
                            .product(productDetail.getProduct())
                            .build();
                    return image;
                }catch (IOException e){
                    throw new AppException(ErrorCode.READ_WRITE_ERROR);
                }
            }
        }else {
            throw new RuntimeException("Invalid file type. Only image and video files are allowed.");
        }
        return null;
    }

    public boolean isValidImageOrVideo(String fileType) {
        return fileType != null && (fileType.startsWith("image/") || fileType.startsWith("video/"));
    }

    public String writeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }
}
