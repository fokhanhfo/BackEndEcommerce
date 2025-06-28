package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Notification.Notification;
import com.projectRestAPI.MyShop.model.SanPham.Product;
import com.projectRestAPI.MyShop.repository.NotificationRepository;
import com.projectRestAPI.MyShop.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification,Long, NotificationRepository> implements NotificationService {
    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        Page<Notification> response = getAll(params,pageable,sort,null);
        List<Notification> notifications = response.getContent();
        ResponseObject responseObject = ResponseObject.builder()
                .status("Success")
                .message("Lấy dữ liệu thành công")
                .errCode(200)
                .data(new HashMap<String,Object>(){{
                    put("notifications",notifications);
                    put("count",response.getTotalElements());
                }})
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
