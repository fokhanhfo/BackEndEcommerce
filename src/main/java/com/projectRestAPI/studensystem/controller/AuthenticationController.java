package com.projectRestAPI.studensystem.controller;

import com.nimbusds.jose.JOSEException;
import com.projectRestAPI.studensystem.dto.request.AuthenticationRequest;
import com.projectRestAPI.studensystem.dto.request.IntrospectRequest;
import com.projectRestAPI.studensystem.dto.request.LogoutRequest;
import com.projectRestAPI.studensystem.dto.request.RefreshRequest;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request),HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody IntrospectRequest request) throws Exception {
        return new ResponseEntity<>(authenticationService.introspect(request),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) throws Exception {
        authenticationService.logout(request);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> authenticate(@RequestBody RefreshRequest request) throws Exception {
        return authenticationService.refreshToken(request);
    }
}
