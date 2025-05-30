package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryRequest {
    @NotNull
    private Long restaurantId;

    @NotBlank
    private String name;
}
