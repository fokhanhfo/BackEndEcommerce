package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.model.Roles;
import com.projectRestAPI.MyShop.repository.RolesRepository;
import com.projectRestAPI.MyShop.service.RolesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl extends BaseServiceImpl<Roles,Long, RolesRepository> implements RolesService {
    @Override
    public List<Roles> findAllById(List<Long> id) {
        return repository.findAllById(id);
    }

    public List<String> listRoleAdmin() {
        return repository.findAll().stream()
                .map(Roles::getName)
                .filter(role -> !"USER".equals(role))
                .toList();
    }

}
