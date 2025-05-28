package com.foodease.myapp.service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SliderImageResponse {
    private Long id;
    private String imageUrl;
    private String link;
    private Integer sortOrder;
}
