package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.model.Category;
import com.projectRestAPI.studensystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin({"http://localhost:3000", "http://127.0.0.1:5500"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        if (categoryService.isCategoryExists(category.getName())) {
            return new  ResponseEntity<Category>(category,HttpStatus.NOT_FOUND);
        }

        // Nếu không trùng tên, lưu danh mục vào cơ sở dữ liệu
        categoryService.createNew(category);
        return new  ResponseEntity<Category>(category,HttpStatus.OK);
    }


    @GetMapping("getAll")
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        try{
            Optional<Category> category=categoryService.findById(id);
            if (category.isPresent()) {
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@RequestBody Category category, @PathVariable Long id){
        try{
            Optional<Category> updateCategory=categoryService.findById(id);
            categoryService.createNew(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        categoryService.delete(id);
        return "Deleted Category with id" +id;
    }
}
