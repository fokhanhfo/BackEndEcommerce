package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.model.Roles;

import java.util.List;

public interface RolesService extends BaseService<Roles,Long> {
    public List<Roles> findAllById(List<Long> id);
}
