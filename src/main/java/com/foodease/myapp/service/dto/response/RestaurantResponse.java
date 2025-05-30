package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Long ownerId;
}
