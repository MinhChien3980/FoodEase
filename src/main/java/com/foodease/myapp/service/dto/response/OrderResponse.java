package com.foodease.myapp.service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String activeStatus;
    private List<Item> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private Long id, menuItemId;
        private Integer quantity;
        private BigDecimal price;
    }
}
