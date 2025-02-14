package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.model.Permission;
import com.projectRestAPI.MyShop.repository.PermissionRepository;
import com.projectRestAPI.MyShop.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long, PermissionRepository> implements PermissionService {
    @Override
    public void savePermissions(List<String> permissions) {
        for (String permissionName : permissions) {
            if (!repository.existsByName(permissionName)) {
                Permission permission = new Permission();
                permission.setName(permissionName);
                repository.save(permission);
            }
        }
    }
//        @Autowired
//        private ModelMapper mapper;
//
//        public ResponseEntity<?> create(PermissionRequest request){
//            Permission permission = repository.save(mapper.map(request, Permission.class));
//            PermissionResponse permissionResponse = mapper.map(permission, PermissionResponse.class);
//            return new ResponseEntity<>(new ResponseObject("success","Thêm thành công",1,permissionResponse), HttpStatus.OK);
//        }
}
