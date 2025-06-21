package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SliderImageRequest {
    @NotBlank
    private String imageUrl;
    private String link;
    @NotNull
    private Integer sortOrder;
}
