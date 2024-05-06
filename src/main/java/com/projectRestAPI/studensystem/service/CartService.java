package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.model.Cart;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService extends BaseService<Cart,Long>{
    public List<Cart> getCart();

    public void deleteAll();
}
