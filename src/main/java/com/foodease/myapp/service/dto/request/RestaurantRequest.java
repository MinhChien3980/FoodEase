package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequest {
    @NotBlank
    private String name;

    private String description;

    private String address;

    private String phone;
}
