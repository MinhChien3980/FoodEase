package com.foodease.myapp.domain.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConfirmPaymentRequest {
    @Schema(description = "Stripe payment intent ID", example = "pi_abc123", required = true)
    private String paymentIntentId;


}
