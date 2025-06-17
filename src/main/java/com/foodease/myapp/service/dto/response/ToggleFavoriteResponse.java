package com.foodease.myapp.service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToggleFavoriteResponse {
    private Long menuItemId;
    private Boolean isFavorite;
} 