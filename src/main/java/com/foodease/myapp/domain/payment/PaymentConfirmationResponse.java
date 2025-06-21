package com.foodease.myapp.domain.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmationResponse {
    private String id;
    private String status;
    private String clientSecret;

    public PaymentConfirmationResponse(String id, String status, String clientSecret) {
        this.id = id;
        this.status = status;
        this.clientSecret = clientSecret;
    }
}
