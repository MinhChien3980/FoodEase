package com.foodease.myapp.service.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private Long userId;
    private Long orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
} 