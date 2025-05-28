package com.foodease.myapp.controller;

import com.foodease.myapp.service.*;
import com.foodease.myapp.service.dto.request.*;
import com.foodease.myapp.service.dto.response.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart", description="Manage shopping cart")
@RequiredArgsConstructor
@Validated
public class CartController {
    private final CartService cartService;

    @PutMapping
    public ApiResponse<CartResponse> update(@RequestBody CartRequest req) {
        CartResponse updated = cartService.upsertCart(req);
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .data(updated)
                .build();
    }

    @GetMapping
    public ApiResponse<CartResponse> get(@RequestParam Long userId) {
        CartResponse cart = cartService.getCart(userId);
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .data(cart)
                .build();
    }
}
