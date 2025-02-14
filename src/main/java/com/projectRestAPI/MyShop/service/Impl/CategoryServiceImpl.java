package com.projectRestAPI.MyShop.service.Impl;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.repository.CategoryRepository;
import com.projectRestAPI.MyShop.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category,Long,CategoryRepository> implements CategoryService {
    @Override
    public boolean isCategoryExists(String categoryName) {
        return repository.existsByName(categoryName);
    }

    @Override
    public boolean isCategoryExistsIdNot(String name, Long id) {
        return repository.existsByNameAndIdNot(name,id);
    }
}
