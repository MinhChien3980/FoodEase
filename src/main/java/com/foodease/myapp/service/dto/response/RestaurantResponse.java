package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String imageUrl;
    private BigDecimal rating;
    private Integer deliveryTime;
    private Boolean isOpen;
    private Map<String, String> openingHours;
    private List<String> cuisine;
    private PriceRange priceRange;
    private Location location;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceRange {
        private Long min;
        private Long max;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
}
