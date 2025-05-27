package com.foodease.myapp.controller;

import com.foodease.myapp.service.UserService;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User-scoped", description = "Operations that act on the currently authenticated user")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}/identity")
    public ApiResponse<UserResponse> getIdentity(@PathVariable Long id) throws BadRequestException {
        return ApiResponse.< UserResponse > builder()
                .code(200)
                .data(userService.getLoginIdentity(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserCreationRequest req) throws BadRequestException {
        return ApiResponse.< UserResponse > builder()
                .code(200)
                .data(userService.updateUser(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccount(@PathVariable Long id) throws BadRequestException {
        userService.deleteUser(id);
        return ApiResponse.< Void > builder()
                .code(204)
                .message("User deleted successfully")
                .build();
    }
}
