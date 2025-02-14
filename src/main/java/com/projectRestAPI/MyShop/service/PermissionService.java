package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.model.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission,Long>{
    public void savePermissions(List<String> permissions);
}
