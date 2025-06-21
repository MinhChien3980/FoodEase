package com.foodease.myapp.domain.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreatePaymentIntentRequest {
    @Schema(description = "Amount in smallest currency unit", example = "100000", required = true)
    private Long amount;

    @Schema(description = "Currency code", example = "vnd", required = true)
    private String currency;

    @Schema(description = "Payment method ID", example = "pm_abc123")
    private String paymentMethodId;

    @Schema(description = "Order ID", example = "order_123")
    private String orderId;

    @Schema(description = "Customer email", example = "customer@example.com")
    private String customerEmail;
}
