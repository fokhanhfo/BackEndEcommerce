package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.model.Roles;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.RolesRepository;
import com.projectRestAPI.studensystem.service.RolesService;
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
