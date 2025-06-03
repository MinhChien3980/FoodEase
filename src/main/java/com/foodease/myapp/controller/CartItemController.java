package com.foodease.myapp.controller;

import com.foodease.myapp.service.CartItemService;
import com.foodease.myapp.service.dto.request.CartItemRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.CartItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@Tag(name = "CartItem", description = "CRUD for cart items")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartItemController {

    CartItemService service;

    @GetMapping
    public ApiResponse<List<CartItemResponse>> listByCart(@RequestParam Long cartId) {
        return ApiResponse.<List<CartItemResponse>>builder()
                .code(200)
                .data(service.findByCartId(cartId))
                .build();
    }

    @GetMapping("/by-menu-item")
    public ApiResponse<List<CartItemResponse>> listByMenuItem(@RequestParam Long menuItemId) {
        return ApiResponse.<List<CartItemResponse>>builder()
                .code(200)
                .data(service.findByMenuItemId(menuItemId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CartItemResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CartItemResponse> create(@Valid @RequestBody CartItemRequest dto) {
        return ApiResponse.<CartItemResponse>builder()
                .code(201)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CartItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CartItemRequest dto
    ) {
        return ApiResponse.<CartItemResponse>builder()
                .code(200)
                .data(service.update(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder().code(204).build();
    }

    @GetMapping("/all")
    public ApiResponse<List<CartItemResponse>> getAll() {
        return ApiResponse.<List<CartItemResponse>>builder()
                .code(200)
                .data(service.findAll())
                .build();
    }

    @DeleteMapping("/by-cart/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteByCartId(@PathVariable Long cartId) {
        service.deleteByCartId(cartId);
        return ApiResponse.<Void>builder().code(204).build();
    }
} 