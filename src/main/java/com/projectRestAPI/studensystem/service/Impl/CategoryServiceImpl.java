package com.projectRestAPI.studensystem.service.Impl;
import com.projectRestAPI.studensystem.model.Category;
import com.projectRestAPI.studensystem.repository.CategoryRepository;
import com.projectRestAPI.studensystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category,Long,CategoryRepository> implements CategoryService {
    @Override
    public boolean isCategoryExists(String categoryName) {
        return repository.existsByName(categoryName);
    }
}
