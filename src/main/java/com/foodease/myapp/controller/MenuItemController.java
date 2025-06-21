package com.foodease.myapp.controller;

import com.foodease.myapp.service.MenuItemService;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@Tag(name = "MenuItem", description = "CRUD for menu items")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MenuItemController {

    MenuItemService service;

    @GetMapping
    public ApiResponse<List<MenuItemResponse>> listByCategory(@RequestParam Long categoryId) {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .code(200)
                .data(service.findByCategory(categoryId))
                .build();
    }

    @GetMapping("/by-restaurant")
    public ApiResponse<List<MenuItemResponse>> listByRestaurant(@RequestParam Long restaurantId) {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .code(200)
                .data(service.findByRestaurant(restaurantId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuItemResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<MenuItemResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuItemResponse> create(@Valid @RequestBody MenuItemRequest dto) {
        return ApiResponse.<MenuItemResponse>builder()
                .code(201)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MenuItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequest dto
    ) {
        return ApiResponse.<MenuItemResponse>builder()
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
    public ApiResponse<List<MenuItemResponse>> getAll() {
        return ApiResponse.<List<MenuItemResponse>>builder()
                .code(200)
                .data(service.findAll())
                .build();
    }
}
