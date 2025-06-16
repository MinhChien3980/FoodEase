package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeliveryRequest {
    @NotNull
    private Long orderId;
    
    @NotNull
    private String status;
    
    private LocalDateTime deliveryTime;
}