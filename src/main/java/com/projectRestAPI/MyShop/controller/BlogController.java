package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Blog;
import com.projectRestAPI.MyShop.service.Impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody Blog blog) {
        Blog created = blogService.create(blog);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status("OK")
                        .message("Tạo blog thành công")
                        .errCode(0)
                        .data(created)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        List<Blog> blogs = blogService.getAll();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status("OK")
                        .message("Lấy danh sách thành công")
                        .errCode(0)
                        .data(blogs)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status("OK")
                        .message("Lấy chi tiết thành công")
                        .errCode(0)
                        .data(blog)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Blog blog) {
        Blog updated = blogService.update(id, blog);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status("OK")
                        .message("Cập nhật thành công")
                        .errCode(0)
                        .data(updated)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status("OK")
                        .message("Xoá thành công")
                        .errCode(0)
                        .data(null)
                        .build()
        );
    }

}
