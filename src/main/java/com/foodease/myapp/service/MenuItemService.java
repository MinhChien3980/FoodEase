package com.foodease.myapp.service;

import com.foodease.myapp.repository.MenuItemRepository;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByRestaurantId(long restaurantId) {
        return menuItemRepository.findByRestaurant_Id(restaurantId).stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByCategoryId(long categoryId) {
        return menuItemRepository.findByCategory_Id(categoryId).stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByRestaurantAndCategoryId(MenuItemRequest request) {
        return menuItemRepository.findByRestaurant_IdAndCategory_Id(
                        request.getRestaurantId(), request.getCategoryId()
                ).stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
