package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartResponse {
    private Long cartItemId;
    private Long menuItemId;
    private Integer quantity;
    private Long totalPrice;
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