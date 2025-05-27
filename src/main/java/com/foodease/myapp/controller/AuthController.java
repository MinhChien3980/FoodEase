package com.foodease.myapp.controller;

import com.foodease.myapp.service.AuthService;
import com.foodease.myapp.service.dto.request.AuthRequest;
import com.foodease.myapp.service.dto.request.IntrospectRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.AuthResponse;
import com.foodease.myapp.service.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
//    @PostMapping("/register")
//    ApiResponse<AuthResponse> register(@RequestBody AuthRequest authRequest) {
//        AuthResponse re = authService.register(authRequest);
//        return ApiResponse.<AuthResponse>builder().code(201)
//                .data(re)
//                .build();
//    }
//    @PostMapping("/refresh")
//    ApiResponse<AuthResponse> authenticate(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        var result = authService.refreshToken(request);
//        return ApiResponse.<AuthResponse>builder().data(result).build();
//    }
//
//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authService.logout(request);
//        System.out.println("Logout");
//        return ApiResponse.<Void>builder().code(200).message("Đăng xuất thành công").build();
//    }
}

