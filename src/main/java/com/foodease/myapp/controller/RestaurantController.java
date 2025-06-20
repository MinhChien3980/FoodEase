package com.foodease.myapp.controller;

import com.foodease.myapp.service.RestaurantService;
import com.foodease.myapp.service.MenuItemService;
import com.foodease.myapp.service.dto.request.RestaurantRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import com.foodease.myapp.service.dto.response.RestaurantWithItemsResponse;
import com.foodease.myapp.service.dto.response.PaginatedMenuItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
@Tag(name="Restaurant", description="CRUD operations for restaurants")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RestaurantController {

    RestaurantService service;
    MenuItemService menuItemService;

    @GetMapping
    public ApiResponse<List<RestaurantResponse>> list(
            @RequestParam(name="q", required=false) String q
    ) {
        return ApiResponse.<List<RestaurantResponse>>builder()
                .code(200)
                .data(service.findAll(q))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RestaurantResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<RestaurantResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RestaurantResponse> create(
            @Valid @RequestBody RestaurantRequest dto
    ) {
        return ApiResponse.<RestaurantResponse>builder()
                .code(200)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RestaurantResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest dto
    ) {
        return ApiResponse.<RestaurantResponse>builder()
                .code(200)
                .data(service.update(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .build();
    }

    @GetMapping("/with-items")
    public ApiResponse<List<RestaurantWithItemsResponse>> getAllRestaurantsWithItems() {
        List<RestaurantWithItemsResponse> data = service.findAllWithItems();
        return ApiResponse.<List<RestaurantWithItemsResponse>>builder()
                .code(200)
                .data(data)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<RestaurantWithItemsResponse>> all() {
        return ApiResponse.<List<RestaurantWithItemsResponse>>builder()
                .code(200)
                .data(service.getAll())
                .build();
    }

    @GetMapping("/{restaurantId}/menu-items")
    public ApiResponse<PaginatedMenuItemResponse> getMenuItems(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        PaginatedMenuItemResponse menuItems = menuItemService.getMenuItemsByRestaurant(
                restaurantId, categoryId, search, page, limit);
        return ApiResponse.<PaginatedMenuItemResponse>builder()
                .code(200)
                .message("Success")
                .data(menuItems)
                .build();
    }
}
