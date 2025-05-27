package com.foodease.myapp.service;

import com.foodease.myapp.repository.MenuItemRepository;
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
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByCategoryId(long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId).stream()
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> findByRestaurantAndCategoryId(long restaurantId, long categoryId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .filter(menuItem -> menuItem.getCategory() != null && menuItem.getCategory().getId() == categoryId)
                .map(MenuItemResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
