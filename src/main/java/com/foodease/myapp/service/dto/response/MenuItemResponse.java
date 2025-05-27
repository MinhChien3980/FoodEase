package com.foodease.myapp.service.dto.response;

import com.foodease.myapp.domain.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MenuItemResponse {
    String name;
    String description;
    double price;
    String imageUrl;
    Long categoryId;
    Long restaurantId;

    public static MenuItemResponse fromEntity(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice() != null ? menuItem.getPrice().doubleValue() : 0.0)
                .imageUrl(menuItem.getImageUrl())
                .categoryId(menuItem.getCategory() != null ? menuItem.getCategory().getId() : null)
                .restaurantId(menuItem.getRestaurant().getId())
                .build();
    }


}
