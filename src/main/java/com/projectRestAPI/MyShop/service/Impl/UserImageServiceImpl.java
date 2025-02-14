package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.UserImage;
import com.projectRestAPI.MyShop.repository.UserImageRepository;
import com.projectRestAPI.MyShop.service.UserImageService;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public class UserImageServiceImpl extends BaseServiceImpl<UserImage,Long, UserImageRepository> implements UserImageService {
    @Override
    public ResponseEntity<ResponseObject> add(SizeRequest sizeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> update(SizeRequest sizeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }
}
