package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.dto.request.ProductRequest;
import com.projectRestAPI.studensystem.model.Product;
import com.projectRestAPI.studensystem.repository.ProductRepository;
import com.projectRestAPI.studensystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl extends BaseServiceImpl<Product , Long , ProductRepository> implements ProductService{
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public int getQuantity(Long id) {
        return repository.findQuantityById(id);
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable, Long id) {
        return repository.findProductByCategory(pageable,id);
    }
}
