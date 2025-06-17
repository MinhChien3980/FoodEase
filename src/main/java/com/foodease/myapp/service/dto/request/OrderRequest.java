package com.foodease.myapp.service.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    Long userId;
    @NotNull
    Long totalPrice;
    @NotNull
    List<Item> items;

    String activeStatus;
    LocalDateTime createdAt;
    String paymentMethod;

    @Data
    public static class Item {
        @NotNull
        Long menuItemId;
        @NotNull
        Integer quantity;
        @NotNull
        Long price;
    }
}
