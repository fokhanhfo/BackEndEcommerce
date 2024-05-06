package com.projectRestAPI.studensystem.controller;

import com.projectRestAPI.studensystem.dto.request.RolesRequest;
import com.projectRestAPI.studensystem.model.Permission;
import com.projectRestAPI.studensystem.model.Roles;
import com.projectRestAPI.studensystem.repository.PermissionRepository;
import com.projectRestAPI.studensystem.service.PermissionService;
import com.projectRestAPI.studensystem.service.RolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
        List<Permission> permissions = permissionRepository.findAllById(rolesRequest.getPermissions());
        roles.setPermissions(permissions);
        return rolesService.createNew(roles);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(rolesService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return rolesService.delete(id);
    }
}
