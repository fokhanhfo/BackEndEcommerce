package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface DashboardService {
    ResponseEntity<ResponseObject> getStatCardDashboard();
}
