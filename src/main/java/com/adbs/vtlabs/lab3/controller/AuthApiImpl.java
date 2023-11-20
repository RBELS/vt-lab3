package com.adbs.vtlabs.lab3.controller;

import com.adbs.vtlabs.api.books.controllers.AuthApi;
import com.adbs.vtlabs.api.books.controllers.models.AuthLoginPost200Response;
import com.adbs.vtlabs.api.books.controllers.models.AuthLoginPostRequest;
import com.adbs.vtlabs.lab3.service.AuthorityService;
import com.adbs.vtlabs.lab3.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiImpl implements AuthApi {
    private final UserService userService;
    private final AuthorityService authorityService;

    @Override
    public ResponseEntity<AuthLoginPost200Response> authLoginPost(@Valid AuthLoginPostRequest authLoginPostRequest) {
        return ResponseEntity.ok(
                new AuthLoginPost200Response().token(authorityService.generateUserJwt(
                        userService.loginUser(authLoginPostRequest.getUsername(), authLoginPostRequest.getPassword())
                ))
        );
    }

    @Override
    public ResponseEntity<AuthLoginPost200Response> authRegisterPost(@Valid AuthLoginPostRequest authLoginPostRequest) {
        return ResponseEntity.ok(
                new AuthLoginPost200Response().token(authorityService.generateUserJwt(
                        userService.registerUser(authLoginPostRequest.getUsername(), authLoginPostRequest.getPassword())
                ))
        );
    }
}
