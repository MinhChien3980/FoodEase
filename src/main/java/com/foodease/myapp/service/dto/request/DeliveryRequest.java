package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryRequest {
    @NotNull
    private Long orderId;
    
    @NotNull
    private String status;
} 