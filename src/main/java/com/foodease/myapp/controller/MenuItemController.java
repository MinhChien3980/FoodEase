package com.foodease.myapp.controller;

import com.foodease.myapp.service.MenuItemService;
import com.foodease.myapp.service.dto.request.AuthRequest;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MenuItemController {
    MenuItemService menuItemService;

    @GetMapping("/all")
    public ApiResponse<List<MenuItemResponse>> getAllMenuItems() {
        List<MenuItemResponse> menuItems = menuItemService.getAllMenuItems();
        return ApiResponse.<List<MenuItemResponse>>builder()
                .code(200)
                .data(menuItems)
                .build();
    }

    @GetMapping("/restaurant/{id}")
    public ApiResponse<MenuItemResponse> getMenuItemByRestaurantId(@PathVariable Long id) {
        List<MenuItemResponse> menuItems = menuItemService.findByRestaurantId(id);
        return ApiResponse.<MenuItemResponse>builder()
                .code(200)
                .data((MenuItemResponse) menuItems)
                .build();
    }

    @GetMapping("/category/{id}")
    public ApiResponse<MenuItemResponse> getMenuItemByCategoryIdId(@PathVariable Long id) {
        List<MenuItemResponse> menuItems = menuItemService.findByCategoryId(id);
        return ApiResponse.<MenuItemResponse>builder()
                .code(200)
                .data((MenuItemResponse) menuItems)
                .build();
    }



    @GetMapping("/restaurant/{id}/category/{categoryId}")
    public ApiResponse<List<MenuItemResponse>> getMenuItemByRestaurantAndCategoryId(
            @PathVariable("id") Long restaurantId,
            @PathVariable("categoryId") Long categoryId) {
        List<MenuItemResponse> menuItems = menuItemService.findByRestaurantAndCategoryId(restaurantId, categoryId);
        return ApiResponse.<List<MenuItemResponse>>builder()
                .code(200)
                .data(menuItems)
                .build();
    }

}
