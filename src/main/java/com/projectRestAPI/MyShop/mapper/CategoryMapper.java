package com.projectRestAPI.MyShop.mapper;

import com.projectRestAPI.MyShop.dto.request.CategoryRequest;
import com.projectRestAPI.MyShop.dto.response.CategoryResponse;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest categoryRequest);

    Category toCategory(Category category);

    CategoryResponse toCategoryResponse(Category category);
}
