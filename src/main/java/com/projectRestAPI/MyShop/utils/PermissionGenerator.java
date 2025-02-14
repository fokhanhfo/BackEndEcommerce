package com.projectRestAPI.MyShop.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionGenerator {

    public List<String> generatePermissions(Class<?> controllerClass) {
        List<String> permissions = new ArrayList<>();

        // Lấy giá trị của @RequestMapping tại cấp lớp
        String basePath = "";
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            String[] basePaths = controllerClass.getAnnotation(RequestMapping.class).value();
            basePath = basePaths.length > 0 ? basePaths[0] : "";
        }

        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                String path = method.getAnnotation(GetMapping.class).value().length > 0
                        ? method.getAnnotation(GetMapping.class).value()[0]
                        : "";
                permissions.add(generatePermissionName("get", basePath, path));
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                String path = method.getAnnotation(PostMapping.class).value().length > 0
                        ? method.getAnnotation(PostMapping.class).value()[0]
                        : "";
                permissions.add(generatePermissionName("add", basePath, path));
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                String path = method.getAnnotation(PutMapping.class).value().length > 0
                        ? method.getAnnotation(PutMapping.class).value()[0]
                        : "";
                permissions.add(generatePermissionName("edit", basePath, path));
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                String path = method.getAnnotation(DeleteMapping.class).value().length > 0
                        ? method.getAnnotation(DeleteMapping.class).value()[0]
                        : "";
                permissions.add(generatePermissionName("delete", basePath, path));
            }
        }

        return permissions;
    }

    private String generatePermissionName(String action, String basePath, String path) {
        basePath = basePath.replaceAll("^/+", "").replaceAll("/", "_"); // Xóa "/" đầu và thay "/" bằng "_"
        path = path.replaceAll("\\{.*?\\}", "id") // Thay thế {id} thành id
                .replaceAll("^/+", "") // Xóa "/" đầu
                .replaceAll("/", "_"); // Thay "/" bằng "_"
        return action + "_" + basePath + "_" + path;
    }
}
