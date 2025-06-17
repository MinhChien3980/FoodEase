package com.foodease.myapp.service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemSummary {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
}
