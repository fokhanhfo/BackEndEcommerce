package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.model.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category,Long> {
    public boolean isCategoryExists(String categoryName);
}
