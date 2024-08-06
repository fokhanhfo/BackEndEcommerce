package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.Exception.AppException;
import com.projectRestAPI.studensystem.Exception.ErrorCode;
import com.projectRestAPI.studensystem.dto.param.ProductParam;
import com.projectRestAPI.studensystem.dto.request.ProductRequest;
import com.projectRestAPI.studensystem.dto.response.CartResponse;
import com.projectRestAPI.studensystem.dto.response.ProductResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.enums.StatusProduct;
import com.projectRestAPI.studensystem.model.Cart;
import com.projectRestAPI.studensystem.model.Category;
import com.projectRestAPI.studensystem.model.Image;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.repository.custom.ProductRepositoryCustom;
import com.projectRestAPI.studensystem.service.CategoryService;
import com.projectRestAPI.studensystem.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl extends BaseServiceImpl<Product , Long , ProductRepository> implements ProductService{

    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepositoryCustom productRepositoryCustom;

    @Override
    public ResponseEntity<ResponseObject> addProduct(ProductRequest productRequest) {
        Optional<Category> categoryOptional = categoryService.findById(productRequest.getCategory());
        if(repository.existsByName(productRequest.getName())){
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        if(categoryOptional.isEmpty()){
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Category category = categoryOptional.get();
        Product product = mapper.map(productRequest,Product.class);
        List<Image> images = productRequest.getImages().stream()
                .map(image -> saveImageFile(image,product))
                .collect(Collectors.toList());
        product.setImages(images);
        product.setCategory(category);
        product.setStatus(StatusProduct.PRODUCT_ACTIVE.getStatus());
        createNew(product);
        ProductResponse productResponse = mapToResponse(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Thêm sản phẩm thành công",200,productResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        List<ProductResponse> productResponses = findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,productResponses),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isEmpty()){
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = productOptional.get();
        ProductResponse productResponse = mapToResponse(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,productResponse),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(Long id,ProductRequest productRequest){
        Optional<Category> categoryOptional = categoryService.findById(productRequest.getCategory());
        if(categoryOptional.isEmpty()){
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Category category = categoryOptional.get();
        Optional<Product> productOptional = findById(id);
        if(productOptional.isEmpty()){
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = productOptional.get();
        product.setName(productRequest.getName());
        product.setDetail(productRequest.getDetail());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(category);
        createNew(product);
        ProductResponse productResponse = mapToResponse(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Update sản phẩm thành công",200,productResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatus(Long id, Integer status) {
        Product product = findById(id).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(status);
        createNew(product);
        ProductResponse productResponse = mapToResponse(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Update trạng thái sản phẩm thành công",200,productResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllProduct(Pageable pageable, ProductParam productParam) {
        List<String> searchKeywords = Arrays.asList(productParam.getSearch().split("\\s+"));
        Page<Product> productsPage = productRepositoryCustom.findProductsByCriteria(
                productParam.getCategoryId(),
                productParam.getPriceGte(),
                productParam.getPriceLte(),
                productParam.getSort(),
                searchKeywords,
                pageable
        );
        List<Product> products = productsPage.getContent();
        List<ProductResponse> productResponses = products.stream()
                .map(this::mapToResponse).toList();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,productResponses),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getCount() {
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,repository.getCount()),HttpStatus.OK);
    }

    private Image saveImageFile(MultipartFile file, Product product){
        if(file != null && !file.isEmpty()){
            String fileType = file.getContentType();
            if(isValidImageOrVideo(fileType)){
                try {
                    String fileName = writeFile(file);
                    Image image = Image.builder()
                            .name(fileName)
                            .file(file.getBytes())
                            .type(fileType)
                            .product(product)
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

    private ProductResponse mapToResponse(Product product){
        ProductResponse productResponse =  mapper.map(product,ProductResponse.class);
        productResponse.setImagesUrl(product.getImages().stream()
                .map(image -> "http://localhost:8080/image/" + image.getName()).collect(Collectors.toList()));
        return productResponse;
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
