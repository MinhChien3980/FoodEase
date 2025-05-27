package com.foodease.myapp.controller;

import com.foodease.myapp.service.AuthService;
import com.foodease.myapp.service.UserService;
import com.foodease.myapp.service.dto.request.AuthRequest;
import com.foodease.myapp.service.dto.request.IntrospectRequest;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.AuthResponse;
import com.foodease.myapp.service.dto.response.IntrospectResponse;
import com.foodease.myapp.service.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthController {
    AuthService authService;
    UserService userService;

    @PostMapping("/token")
    ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse re = authService.authenticate(authRequest);
        return ApiResponse.<AuthResponse>builder().code(200)
                .data(re)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var re = authService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .data(re)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<String> register(@RequestBody UserCreationRequest req) throws BadRequestException {
        var login = userService.createUser(req).getLogin();
        return ApiResponse.<String>builder()
                .code(201)
                .message("User created successfully")
                .data(login)
                .build();
    }
}

