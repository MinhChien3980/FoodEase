package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantWithItemsResponse {
    private Long id;
    private String name;
    private String address;
    private List<MenuItemSummary> menuItems;
}
