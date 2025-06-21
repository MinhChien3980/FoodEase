// CategoryGroupResponse.java
package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGroupResponse {
    private String name;                      // tên category
    private List<RestaurantGroupResponse> restaurants;
}
