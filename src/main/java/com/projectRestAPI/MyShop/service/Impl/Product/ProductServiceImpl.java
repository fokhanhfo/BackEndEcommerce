package com.projectRestAPI.MyShop.service.Impl.Product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.ProductStatistics.*;
import com.projectRestAPI.MyShop.dto.request.ProductDetailRequest;
import com.projectRestAPI.MyShop.dto.request.ProductDetailSizeRequest;
import com.projectRestAPI.MyShop.dto.request.ProductRequest;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ProductResponse;
import com.projectRestAPI.MyShop.dto.response.ProductStatisticsResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.enums.StatusProduct;
import com.projectRestAPI.MyShop.mapper.*;
import com.projectRestAPI.MyShop.model.Image;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetail;
import com.projectRestAPI.MyShop.model.SanPham.ProductDetailSize;
import com.projectRestAPI.MyShop.repository.ImageRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailRepository;
import com.projectRestAPI.MyShop.repository.ProductDetailSizeRepository;
import com.projectRestAPI.MyShop.repository.ProductRepository;
import com.projectRestAPI.MyShop.repository.custom.ProductRepositoryCustom;
import com.projectRestAPI.MyShop.service.CategoryService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.utils.FileUploadUtils;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDetailSizeRepository productDetailSizeRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private SizeMapper sizeMapper;

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;



    @Override
    public ResponseEntity<ResponseObject> addProduct(ProductRequest productRequest, MultiValueMap<String, MultipartFile> images) {
        if (repository.existsByName(productRequest.getName()) && productRequest.getId() == null) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        Product product = productMapper.toProduct(productRequest);
        Product savedProduct = repository.save(product);

        List<ProductDetail> productDetails = product.getProductDetails();
        List<ProductDetail> productDetailsSave = new ArrayList<>();
        IntStream.range(0, productDetails.size()).forEach(i -> {
            ProductDetail detail = productDetails.get(i);
            String key = "productDetails." + i;
            if (images.containsKey(key)) {
                List<MultipartFile> productDetailImages = FileUploadUtils.getImagesByIndex(images, i);
                List<Image> savedImages = null;
                try {
                    savedImages = imageRepository.saveAll(saveImageFiles(i,productDetailImages, product, detail.getColor(), detail,productRequest.getProductDetails().get(i),productRequest));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                detail.setImage(savedImages);
            }
            detail.setProduct(savedProduct);
            List<ProductDetailSize> productDetailSize = detail.getProductDetailSizes().stream()
                    .peek(productDetailSize1 -> productDetailSize1.setProductDetail(detail))
                    .toList();
        });

        savedProduct.setStatus(StatusProduct.PRODUCT_ACTIVE.getStatus());

        repository.save(savedProduct);

        ProductResponse productResponse = productMapper.toProductResponse(savedProduct);

        return new ResponseEntity<>(new ResponseObject("Success", "Thêm sản phẩm thành công", 200, productResponse), HttpStatus.OK);
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

    @Transactional
    @Override
    public ResponseEntity<ResponseObject> update(ProductRequest productRequest, MultiValueMap<String, MultipartFile> images) {
        if (productRequest.getId() == null && repository.existsByName(productRequest.getName())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        Product product = productMapper.toProduct(productRequest);

        // Lấy danh sách ProductDetail một lần duy nhất
        Map<Long, ProductDetail> existingDetails = productDetailRepository.findAllById(
                        product.getProductDetails().stream().map(ProductDetail::getId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(ProductDetail::getId, Function.identity()));

        List<ProductDetail> productDetailsSave = new ArrayList<>();

        for (int i = 0; i < product.getProductDetails().size(); i++) {
            ProductDetail detail = product.getProductDetails().get(i);
            String key = "productDetails." + i;

            if (images.containsKey(key)) {
                try {
                    List<MultipartFile> productDetailImages = FileUploadUtils.getImagesByIndex(images, i);
                    List<Image> savedImages = imageRepository.saveAll(
                            saveImageFiles(i, productDetailImages, product, detail.getColor(), detail,
                                    productRequest.getProductDetails().get(i), productRequest)
                    );

                    // Chỉ lấy ảnh từ existingDetails nếu nó có trong danh sách
                    if (existingDetails.containsKey(detail.getId())) {
                        savedImages.addAll(existingDetails.get(detail.getId()).getImage());
                    }
                    detail.setImage(savedImages);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else if (existingDetails.containsKey(detail.getId())) {
                int finalI = i;
                List<Image> imageList = existingDetails.get(detail.getId()).getImage().stream()
                        .peek(image -> {
                            image.setMainColor(Objects.equals(image.getId(), productRequest.getProductDetails().get(finalI).getIsMainId()));
                            image.setMainProduct(Objects.equals(image.getId(), productRequest.getIsMainProductId()));
                            image.setColor(detail.getColor());
                        })
                        .toList();
                detail.setImage(imageList);
            }
            if(productRequest.getProductDetails().get(i).getProductDetailSizes().stream().anyMatch((productDetailSizeRequest -> productDetailSizeRequest.getId() == null))){
                List<ProductDetailSize> productDetailSizes = productDetailSizeRepository.saveAll(saveProductDetailSize(detail, productRequest.getProductDetails().get(i)));
//                if (existingDetails.containsKey(detail.getId())) {
//                    productDetailSizes.addAll(existingDetails.get(detail.getId()).getProductDetailSizes());
//                }
                detail.setProductDetailSizes(productDetailSizes);
            }else if (existingDetails.containsKey(detail.getId())) {
                List<ProductDetailSize> productDetailSizes =
                        IntStream.range(0, existingDetails.get(detail.getId()).getProductDetailSizes().size())
                                .mapToObj(index -> {
                                    ProductDetailSize productDetailSize = existingDetails.get(detail.getId()).getProductDetailSizes().get(index);

                                    productDetailSize.setQuantity(detail.getProductDetailSizes().get(index).getQuantity());
                                    productDetailSize.setSize(detail.getProductDetailSizes().get(index).getSize());
                                    return productDetailSize;
                                })
                                .toList();

                detail.setProductDetailSizes(productDetailSizes);
            }
            detail.setProduct(product);
            productDetailsSave.add(detail);
        }

        productDetailRepository.saveAll(productDetailsSave);
        product.setProductDetails(productDetailsSave);
        product.setStatus(StatusProduct.PRODUCT_ACTIVE.getStatus());

        repository.save(product);

        ProductResponse productResponse = productMapper.toProductResponse(product);
        return ResponseEntity.ok(new ResponseObject("Success", "Sản phẩm cập nhật thành công", 200, productResponse));
    }


    private List<ProductDetail> convertData(List<ProductDetail> productDetail,List<ProductDetail> productDetail1){
        return null;
    }



    @Override
    public ResponseEntity<ResponseObject> updateStatus(Long id, Integer status) {
        Product product = findById(id).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(status);
        createNew(product);
        return new ResponseEntity<>(new ResponseObject("Succes","Update trạng thái sản phẩm thành công",200,null), HttpStatus.OK);
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

    private List<Image> saveImageFiles(
            int index, List<MultipartFile> files, Product product,
            Color color, ProductDetail detail, ProductDetailRequest productDetailRequest,
            ProductRequest productRequest) throws JsonProcessingException {

        List<Image> savedImages = new ArrayList<>();

        // Lưu ProductDetail nếu chưa có ID (gộp vào lưu cuối cùng)
        boolean isNewDetail = (detail.getId() == null);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = Optional.ofNullable(productRequest.getIsMainProductIdNew())
                .filter(StringUtils::hasText)
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
                    } catch (JsonProcessingException e) {
                        return Collections.<String, Object>emptyMap(); // Fix lỗi này
                    }
                }).orElse(Collections.emptyMap());


        if (files == null || files.isEmpty()) {
            return savedImages;
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) continue;

            String fileType = file.getContentType();
            if (!isValidImageOrVideo(fileType)) {
                throw new RuntimeException("Invalid file type. Only image and video files are allowed.");
            }

            try {
                String fileName = writeFile(file);
                Image image = Image.builder()
                        .name(fileName)
                        .type(fileType)
                        .product(product)
                        .productDetail(detail)
                        .color(color)
                        .mainColor(StringUtils.hasText(productDetailRequest.getIsMainIdNew()))
                        .build();

                // Kiểm tra `mainProduct`
                Integer mapIndex = (Integer) map.get("index");
                Integer mapIndexFile = (Integer) map.get("indexFile");

                if (mapIndex != null && mapIndexFile != null && mapIndex == index && mapIndexFile == i) {
                    image.setMainProduct(true);
                }

                savedImages.add(image);
            } catch (IOException e) {
                throw new AppException(ErrorCode.READ_WRITE_ERROR);
            }
        }

        if (isNewDetail) {
            productDetailRepository.save(detail);
        }

        return savedImages;
    }

    private List<ProductDetailSize> saveProductDetailSize(
            ProductDetail detail, ProductDetailRequest productDetailRequest){
        List<ProductDetailSize> savedSize = new ArrayList<>();
        for (int i = 0; i < productDetailRequest.getProductDetailSizes().size(); i++) {
            List<ProductDetailSizeRequest> productDetailSizeRequests = productDetailRequest.getProductDetailSizes();
                ProductDetailSize size = ProductDetailSize.builder()
                        .productDetail(detail)
                        .quantity(productDetailSizeRequests.get(i).getQuantity())
                        .size(sizeMapper.toSize(productDetailSizeRequests.get(i).getSize()))
                        .build();
                savedSize.add(size);
        }
        return savedSize;
    }




//    private Image saveImageFile(MultipartFile[] file, Product product, Color color){
//        if(file != null && !file.isEmpty()){
//            String fileType = file.getContentType();
//            if(isValidImageOrVideo(fileType)){
//                try {
//                    String fileName = writeFile(file);
//                    Image image = Image.builder()
//                            .name(fileName)
//                            .file(file.getBytes())
//                            .type(fileType)
//                            .product(product)
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

    @Override
    public ResponseEntity<ResponseObject> getAllProductStatistics() {
        List<ProductStatusDTO> statusStats = repository.getProductCountByStatusNative();
        PageRequest pageRequest = PageRequest.of(0, 1);
        ProductQuantityView topProduct = repository.findTopProductWithMostQuantity(pageRequest).stream().findFirst().orElse(null);
        List<ProductQuantityDTO> productQuantityDTOS = repository.findProductWithTotalQuantityLessThanTenNative();
        ProductSalesView productSalesView = repository.findBestSellingProduct();
        return ResponseUtil.buildResponse("success","Lấy dữ liệu thành công",1,new ProductStatisticsResponse(
                statusStats,
                topProduct,
                productQuantityDTOS,
                productSalesView
        ),HttpStatus.OK);
    }
}
