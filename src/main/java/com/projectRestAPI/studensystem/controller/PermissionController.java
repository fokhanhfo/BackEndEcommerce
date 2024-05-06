package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.PermissionRequest;
import com.projectRestAPI.studensystem.dto.response.PermissionResponse;
import com.projectRestAPI.studensystem.model.Permission;
import com.projectRestAPI.studensystem.service.PermissionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PermissionRequest permissionRequest){
        Permission permission = mapper.map(permissionRequest,Permission.class);
        return permissionService.createNew(permission);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Permission> permissions = permissionService.findAll();
        List<PermissionResponse> permissionResponse = mapper.map(permissions, new TypeToken<List<PermissionResponse>>() {}.getType());
        return new ResponseEntity<>(permissionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
