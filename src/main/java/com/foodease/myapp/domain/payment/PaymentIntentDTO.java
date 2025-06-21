package com.foodease.myapp.domain.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Payment Intent response from Stripe")
public class PaymentIntentDTO {
    @Schema(description = "The ID of the payment intent", example = "pi_3RatAwDQcqQQq2Se2VtatpEx")
    private String id;

    @Schema(description = "The status of the payment intent", example = "succeeded")
    private String status;

    @Schema(description = "The amount of the payment in cents", example = "100000")
    private Long amount;

    @Schema(description = "The currency of the payment", example = "vnd")
    private String currency;

    @Schema(description = "The client secret used to confirm the payment on the client side")
    private String clientSecret;
} 