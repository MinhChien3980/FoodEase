package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantGroupResponse {
    private Long id;
    private String name;
    private String address;
}
