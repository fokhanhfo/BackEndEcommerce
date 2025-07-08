package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.model.Blog;
import com.projectRestAPI.MyShop.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogServiceImpl {
    @Autowired
    private BlogRepository blogRepository;

    public Blog create(Blog blog) {
        if (blogRepository.existsByTitle(blog.getTitle())) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        blog.setCreatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }


    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public Blog getById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BAD_REQUEST));
    }

    public Blog update(Long id, Blog newBlog) {
        Blog existing = getById(id);

        if (!existing.getTitle().equals(newBlog.getTitle())
                && blogRepository.existsByTitle(newBlog.getTitle())) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        existing.setImage(newBlog.getImage());
        existing.setTitle(newBlog.getTitle());
        existing.setContent(newBlog.getContent());
        existing.setApproved(newBlog.isApproved());
        return blogRepository.save(existing);
    }


    public void delete(Long id) {
        Blog blog = getById(id);
        blogRepository.delete(blog);
    }
}
