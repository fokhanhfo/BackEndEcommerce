package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.AuthenticationRequest;
import com.projectRestAPI.MyShop.dto.request.IntrospectRequest;
import com.projectRestAPI.MyShop.dto.request.LogoutRequest;
import com.projectRestAPI.MyShop.dto.request.RefreshRequest;
import com.projectRestAPI.MyShop.dto.response.IntrospectResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthenticationService {
    ResponseEntity<ResponseObject> authenticate(AuthenticationRequest authenticationRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception;

    public void logout(LogoutRequest request) throws Exception;

    public ResponseEntity<?> refreshToken(RefreshRequest request) throws Exception;

    ResponseEntity<?> facebookLogin(Map<String,String> payload);
}
