package com.foodease.myapp.service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private Long cartId;
    private Long menuItemId;
    private Integer quantity;
} 