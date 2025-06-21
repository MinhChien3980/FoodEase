package com.foodease.myapp.service.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private Long userId;
    private Long orderId;
    private String transactionType;
    private String type;
    private String paymentMethod;
    private String txnId;
    private BigDecimal amount;
    private String status;
    private String message;
    private LocalDateTime createdAt;
} 