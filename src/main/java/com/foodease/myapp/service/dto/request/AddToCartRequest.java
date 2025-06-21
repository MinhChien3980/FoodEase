package com.foodease.myapp.service.dto.request;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {
    private Long menuItemId;
    private Integer quantity;
    private String specialInstructions;
    private List<Customization> customizations;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customization {
        private String type;
        private String value;
        private Long extraPrice;
    }
} 