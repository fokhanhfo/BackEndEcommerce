package com.projectRestAPI.MyShop.service.Impl.Product;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.StatusProduct;
import com.projectRestAPI.MyShop.mapper.CategoryMapper;
import com.projectRestAPI.MyShop.mapper.ProductMapper;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.Product;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.repository.ProductRepository;
import com.projectRestAPI.MyShop.repository.custom.ProductRepositoryCustom;
import com.projectRestAPI.MyShop.service.CategoryService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseEntity<ResponseObject> addProduct(ProductRequest productRequest) {
//        Optional<Category> categoryOptional = categoryService.findById(productRequest.getCategory().getId());
        if(repository.existsByName(productRequest.getName())){
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
//        Category category = categoryOptional.get();
        Product product = mapper.map(productRequest,Product.class);
//        List<Image> images = productRequest.getImages().stream()
//                .map(image -> saveImageFile(image,product))
//                .collect(Collectors.toList());
        Category category = categoryMapper.toCategory(productRequest.getCategory());
        category.setId(productRequest.getCategory().getId());
        product.setCategory(category);
        product.setStatus(StatusProduct.PRODUCT_ACTIVE.getStatus());
        repository.save(product);
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
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,productResponse),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(ProductRequest productRequest){
        if(repository.existsByNameAndIdNot(productRequest.getName(),productRequest.getId())){
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        Optional<Product> productOptional = findById(productRequest.getId());
        if(productOptional.isEmpty()){
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = productOptional.get();
        product.setName(productRequest.getName());
        product.setDetail(productRequest.getDetail());
        product.setStatus(productRequest.getStatus());
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
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        Page<Product> response = getAll(params,pageable,sort,null);
        List<Product> products = response.getContent();
        List<ProductResponse> productResponses = productMapper.toListProductResponse(products);
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("products",productResponses);
                    put("count",response.getTotalElements());
                }})
                .build();

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getCount() {
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,repository.getCount()),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getNewProduct(Pageable pageable) {
        Page<Product> productPage = repository.findProductNew(pageable);
        List<Product> productList = productPage.getContent();
        List<ProductResponse> productResponse = productList.stream().map(this::mapToResponse).toList();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,productResponse),HttpStatus.OK);
    }

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

    private ProductResponse mapToResponse(Product product){
        ProductResponse productResponse =  mapper.map(product,ProductResponse.class);
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
