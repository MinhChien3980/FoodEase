package com.foodease.myapp.controller;

import com.foodease.myapp.exception.AppException;
import com.foodease.myapp.exception.ErrorCode;
import com.foodease.myapp.service.UserService;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Tag(name = "User-scoped", description = "Operations that act on the currently authenticated user")
@CrossOrigin(origins = "http://localhost:5173")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("/{id}/identity")
    public ApiResponse<UserResponse> getIdentity(@PathVariable Long id) {
        return ApiResponse.< UserResponse > builder()
                .code(200)
                .data(userService.getLoginIdentity(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserCreationRequest req) {
        return ApiResponse.< UserResponse > builder()
                .code(200)
                .data(userService.updateUser(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccount(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.< Void > builder()
                .code(204)
                .message("User deleted successfully")
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<UserResponse> profile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        String jwt = authHeader.substring(7);
        UserResponse user = userService.getUserByToken(jwt);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .data(user)
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .data(userService.getAllUsers())
                .build();
    }

}
