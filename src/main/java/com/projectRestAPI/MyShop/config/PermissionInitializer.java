package com.projectRestAPI.MyShop.config;

import com.projectRestAPI.MyShop.controller.ProductController;
import com.projectRestAPI.MyShop.service.PermissionService;
import com.projectRestAPI.MyShop.utils.PermissionGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PermissionInitializer {
    @Autowired
    private PermissionGenerator permissionGenerator;

    @Autowired
    private PermissionService permissionService;

    @PostConstruct
    public void initializePermissions() {
        // Quét các Controller và lấy permission
        List<String> productPermissions = permissionGenerator.generatePermissions(ProductController.class);

        // Lưu vào database
        permissionService.savePermissions(productPermissions);

        // Thêm logic quét các controller khác nếu cần
    }
}
