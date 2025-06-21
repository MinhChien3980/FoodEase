package com.foodease.myapp.service.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {
    @NotNull
    Long userId;
    @NotNull
    List<Item> items;

    @Data
    public static class Item {
        @NotNull
        Long menuItemId;
        @NotNull
        Integer quantity;
    }
}
