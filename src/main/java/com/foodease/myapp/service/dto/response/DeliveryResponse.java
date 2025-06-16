package com.foodease.myapp.service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private String status;
    private LocalDateTime deliveryTime;
} 