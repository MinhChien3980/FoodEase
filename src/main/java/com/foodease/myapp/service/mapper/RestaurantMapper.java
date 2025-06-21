package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Restaurant;
import com.foodease.myapp.service.dto.request.RestaurantRequest;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantMapper {
    
    @Autowired
    private ObjectMapper objectMapper;

    public Restaurant toEntity(RestaurantRequest dto) {
        if (dto == null) {
            return null;
        }
        
        return Restaurant.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
    }

    public RestaurantResponse toDto(Restaurant entity) {
        if (entity == null) {
            return null;
        }
        
        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .imageUrl(entity.getImageUrl())
                .rating(entity.getRating())
                .deliveryTime(entity.getDeliveryTime())
                .isOpen(entity.getIsOpen())
                .openingHours(parseJsonToMap(entity.getOpeningHours()))
                .cuisine(parseJsonToList(entity.getCuisineTypes()))
                .priceRange(RestaurantResponse.PriceRange.builder()
                        .min(entity.getMinPrice())
                        .max(entity.getMaxPrice())
                        .build())
                .location(RestaurantResponse.Location.builder()
                        .latitude(entity.getLatitude())
                        .longitude(entity.getLongitude())
                        .build())
                .build();
    }

    public void updateFromDto(RestaurantRequest dto, Restaurant entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
    }
    
    private Map<String, String> parseJsonToMap(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            return Collections.emptyMap();
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
}
