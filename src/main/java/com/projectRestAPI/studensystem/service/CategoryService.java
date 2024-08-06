package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.request.CategoryRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService extends BaseService<Category,Long> {
    public boolean isCategoryExists(String categoryName);

//    public ResponseEntity<ResponseObject> addCategory(CategoryRequest categoryRequest);
//
//    public ResponseEntity<ResponseObject> getIdCategory(Long id);
//
//    public ResponseEntity<ResponseObject> getAllCategory(CategoryRequest categoryRequest);
}
