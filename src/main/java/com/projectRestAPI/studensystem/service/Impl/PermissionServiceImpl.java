package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.dto.request.PermissionRequest;
import com.projectRestAPI.studensystem.dto.response.PermissionResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.Permission;
import com.projectRestAPI.studensystem.repository.PermissionRepository;
import com.projectRestAPI.studensystem.service.PermissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long, PermissionRepository> implements PermissionService {
//        @Autowired
//        private ModelMapper mapper;
//
//        public ResponseEntity<?> create(PermissionRequest request){
//            Permission permission = repository.save(mapper.map(request, Permission.class));
//            PermissionResponse permissionResponse = mapper.map(permission, PermissionResponse.class);
//            return new ResponseEntity<>(new ResponseObject("success","Thêm thành công",1,permissionResponse), HttpStatus.OK);
//        }
}
