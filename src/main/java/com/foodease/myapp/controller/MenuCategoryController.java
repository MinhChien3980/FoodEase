package com.foodease.myapp.controller;

import com.foodease.myapp.service.MenuCategoryService;
import com.foodease.myapp.service.dto.request.MenuCategoryRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.CategoryGroupResponse;
import com.foodease.myapp.service.dto.response.MenuCategoryResponse;
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
@RequestMapping("/categories")
@Tag(name="MenuCategory", description="CRUD for menu categories")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MenuCategoryController {

    MenuCategoryService service;

    @GetMapping
    public ApiResponse<List<MenuCategoryResponse>> list(@RequestParam Long restaurantId) {
        return ApiResponse.<List<MenuCategoryResponse>>builder()
                .code(200)
                .data(service.findAll(restaurantId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuCategoryResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<MenuCategoryResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuCategoryResponse> create(@Valid @RequestBody MenuCategoryRequest dto) {
        return ApiResponse.<MenuCategoryResponse>builder()
                .code(201)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MenuCategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MenuCategoryRequest dto
    ) {
        return ApiResponse.<MenuCategoryResponse>builder()
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

    @GetMapping("/grouped")
    public ApiResponse<List<CategoryGroupResponse>> getGroupedCategories() {
        List<CategoryGroupResponse> data = service.findCategoriesGroupedByName();
        return ApiResponse.<List<CategoryGroupResponse>>builder()
                .code(200)
                .data(data)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<MenuCategoryResponse>> getAllCategories() {
        return ApiResponse.<List<MenuCategoryResponse>>builder()
                .code(200)
                .data(service.findAll())
                .build();
    }
}
