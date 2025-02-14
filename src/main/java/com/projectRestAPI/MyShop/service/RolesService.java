package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.model.Roles;

import java.util.List;

public interface RolesService extends BaseService<Roles,Long> {
    public List<Roles> findAllById(List<Long> id);
}
