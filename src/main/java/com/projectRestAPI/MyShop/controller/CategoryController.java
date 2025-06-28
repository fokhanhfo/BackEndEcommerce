package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.CategoryRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Category category) {
        if(categoryService.isCategoryExists(category.getName())){
            return new ResponseEntity<>(new ResponseObject("Error","Trùng tên danh mục",400,null),HttpStatus.BAD_REQUEST);
        }
        return categoryService.createNew(category);
    }


//    @GetMapping
//    public ResponseEntity<ResponseObject> getAllCategory(){
//        List<Category> categories = categoryService.findAll();
//        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,categories),HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllCategory(){
        return categoryService.getCategory();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> get(@PathVariable Long id){
        Optional<Category> categoryOptional=categoryService.findById(id);
        if (categoryOptional.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("Error","Không tìm thấy danh mục",400,null),HttpStatus.BAD_REQUEST);
        }
        Category category = categoryOptional.get();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,category),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id){
        try{
            if(categoryService.isCategoryExistsIdNot(categoryRequest.getName(),id)){
                return new ResponseEntity<>(new ResponseObject("Error","Trùng tên danh mục",400,null),HttpStatus.BAD_REQUEST);
            }
            Optional<Category> updateCategory=categoryService.findById(id);
            if (updateCategory.isEmpty()){
                return new ResponseEntity<>(new ResponseObject("Error","Không tìm thấy danh mục",404,null),HttpStatus.NOT_FOUND);
            }
            Category category = updateCategory.get();
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            return categoryService.update(category);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(new ResponseObject("Error","Update không thành công",400,null),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
            return categoryService.delete(id);
    }
}
