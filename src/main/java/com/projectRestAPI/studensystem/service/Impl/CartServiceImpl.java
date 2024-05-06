package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.model.Cart;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.CartRepository;
import com.projectRestAPI.studensystem.service.CartService;
import com.projectRestAPI.studensystem.service.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends BaseServiceImpl<Cart,Long, CartRepository> implements CartService {
    @Autowired
    private UsersService usersService;

    @Override
    public List<Cart> getCart() {
        Users users = usersService.getUser();
        return repository.findCartByUser(users.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        Users users = usersService.getUser();
        repository.deleteByUser(users.getId());
    }
}
