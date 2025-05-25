package com.projectRestAPI.MyShop.utils;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUploadUtils {
    public static List<MultipartFile> getImagesByIndex(MultiValueMap<String, MultipartFile> images, int index) {
        String key = "productDetails." + index;
        return images.getOrDefault(key, List.of());
    }
}
