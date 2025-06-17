package com.foodease.myapp.domain.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentIntentResponse {
    @Schema(description = "Stripe client secret", example = "pi_xxx_secret_xxx")
    private String clientSecret;

    @Schema(description = "Stripe payment intent ID", example = "pi_xxx")
    private String paymentIntentId;
}
