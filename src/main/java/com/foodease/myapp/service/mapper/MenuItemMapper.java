package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.MenuItem;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MenuItemMapper {
    
    @Autowired
    private ObjectMapper objectMapper;

    public MenuItem toEntity(MenuItemRequest dto) {
        if (dto == null) {
            return null;
        }
        
        MenuItem entity = new MenuItem();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice() != null ? dto.getPrice().longValue() : null);
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public MenuItemResponse toDto(MenuItem entity) {
        if (entity == null) {
            return null;
        }
        
        return MenuItemResponse.builder()
                .id(entity.getId())
                .restaurantId(entity.getRestaurant() != null ? entity.getRestaurant().getId() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .originalPrice(entity.getOriginalPrice())
                .discountPercentage(entity.getDiscountPercentage())
                .imageUrl(entity.getImageUrl())
                .isVegetarian(entity.getIsVegetarian())
                .isAvailable(entity.getIsAvailable())
                .rating(entity.getRating())
                .reviewsCount(entity.getReviewsCount())
                .preparationTime(entity.getPreparationTime())
                .ingredients(parseJsonToList(entity.getIngredients()))
                .allergens(parseJsonToList(entity.getAllergens()))
                .nutritionalInfo(parseNutritionalInfo(entity.getNutritionalInfo()))
                .spiceLevel(entity.getSpiceLevel())
                .tags(parseJsonToList(entity.getTags()))
                .build();
    }

    public void updateFromDto(MenuItemRequest dto, MenuItem entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice().longValue());
        }
        if (dto.getImageUrl() != null) {
            entity.setImageUrl(dto.getImageUrl());
        }
    }
    
    private List<String> parseJsonToList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    private MenuItemResponse.NutritionalInfo parseNutritionalInfo(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, MenuItemResponse.NutritionalInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
