package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    @NotNull
    private Long cartId;

    @NotNull
    private Long menuItemId;

    @NotNull
    private Integer quantity;
} 