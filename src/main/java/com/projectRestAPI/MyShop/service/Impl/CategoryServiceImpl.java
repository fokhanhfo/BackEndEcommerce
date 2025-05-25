package com.projectRestAPI.MyShop.service.Impl;
import com.projectRestAPI.MyShop.dto.CategoryQuantitySummary;
import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.repository.CategoryRepository;
import com.projectRestAPI.MyShop.service.CategoryService;
import com.projectRestAPI.MyShop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category,Long,CategoryRepository> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isCategoryExists(String categoryName) {
        return repository.existsByName(categoryName);
    }

    @Override
    public boolean isCategoryExistsIdNot(String name, Long id) {
        return repository.existsByNameAndIdNot(name,id);
    }

    @Override
    public ResponseEntity<ResponseObject> getCategory() {
        List<CategoryQuantitySummary> categorys = categoryRepository.getCategoryQuantitySummary();
        return ResponseUtil.buildResponse("succes","lấy dữ liệu thành công",1,categorys, HttpStatus.OK);
    }
}
