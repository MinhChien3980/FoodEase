package com.foodease.myapp.service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryResponse {
    private Long id;
    private Long restaurantId;
    private String name;
}
