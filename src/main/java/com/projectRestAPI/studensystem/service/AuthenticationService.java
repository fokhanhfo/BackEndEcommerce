package com.projectRestAPI.studensystem.service;

import com.nimbusds.jose.JOSEException;
import com.projectRestAPI.studensystem.dto.request.AuthenticationRequest;
import com.projectRestAPI.studensystem.dto.request.IntrospectRequest;
import com.projectRestAPI.studensystem.dto.request.LogoutRequest;
import com.projectRestAPI.studensystem.dto.request.RefreshRequest;
import com.projectRestAPI.studensystem.dto.response.IntrospectResponse;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface AuthenticationService {
    ResponseEntity<ResponseObject> authenticate(AuthenticationRequest authenticationRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception;

    public void logout(LogoutRequest request) throws Exception;

    public ResponseEntity<?> refreshToken(RefreshRequest request) throws Exception;
}
