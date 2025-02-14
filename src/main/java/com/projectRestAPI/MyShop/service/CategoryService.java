package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.model.Category;

public interface CategoryService extends BaseService<Category,Long> {
    public boolean isCategoryExists(String categoryName);

    boolean isCategoryExistsIdNot(String name, Long id);

//    public ResponseEntity<ResponseObject> addCategory(CategoryRequest categoryRequest);
//
//    public ResponseEntity<ResponseObject> getIdCategory(Long id);
//
//    public ResponseEntity<ResponseObject> getAllCategory(CategoryRequest categoryRequest);
}
