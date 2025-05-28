package com.foodease.myapp.service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    Long userId;
    @NotNull
    BigDecimal totalPrice;
    @NotNull
    List<Item> items;

    @Data
    public static class Item {
        @NotNull
        Long menuItemId;
        @NotNull
        Integer quantity;
        @NotNull
        BigDecimal price;
    }
}
