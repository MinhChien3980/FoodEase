package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {
    private Long id;
    private Long restaurantId;
    private Long categoryId;
    private String name;
    private String description;
    private Long price;
    private Long originalPrice;
    private Integer discountPercentage;
    private String imageUrl;
    private Boolean isVegetarian;
    private Boolean isAvailable;
    private BigDecimal rating;
    private Integer reviewsCount;
    private Integer preparationTime;
    private List<String> ingredients;
    private List<String> allergens;
    private NutritionalInfo nutritionalInfo;
    private String spiceLevel;
    private List<String> tags;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NutritionalInfo {
        private Integer calories;
        private Integer protein;
        private Integer carbs;
        private Integer fat;
        private Integer fiber;
        private Integer sugar;
        private Integer sodium;
    }
}
