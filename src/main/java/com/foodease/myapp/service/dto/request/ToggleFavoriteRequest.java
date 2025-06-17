package com.foodease.myapp.service.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToggleFavoriteRequest {
    private Long menuItemId;
} 