package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.PermissionRequest;
import com.projectRestAPI.MyShop.dto.request.RolesRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Permission;
import com.projectRestAPI.MyShop.model.Roles;
import com.projectRestAPI.MyShop.repository.PermissionRepository;
import com.projectRestAPI.MyShop.service.RolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody RolesRequest rolesRequest){
        Roles roles =mapper.map(rolesRequest, Roles.class);
        List<Permission> permissions = permissionRepository.findAllById(
                rolesRequest.getPermissions()
                        .stream()
                        .map(PermissionRequest::getId)
                        .collect(Collectors.toList()));
        roles.setPermissions(permissions);
        return rolesService.createNew(roles);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(new ResponseObject("success","Lấy dữ liệu thành công",200,rolesService.findAll()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return rolesService.delete(id);
    }
}
