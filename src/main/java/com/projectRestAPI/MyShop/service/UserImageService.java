package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.UserImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

public interface UserImageService extends BaseService<UserImage,Long>{
    ResponseEntity<?>  getImage(String name) throws IOException;
}
