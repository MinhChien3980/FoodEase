package com.foodease.myapp.controller;

import com.foodease.myapp.domain.payment.ConfirmPaymentRequest;
import com.foodease.myapp.domain.payment.CreatePaymentIntentRequest;
import com.foodease.myapp.domain.payment.PaymentConfirmationResponse;
import com.foodease.myapp.domain.payment.PaymentIntentResponse;
import com.foodease.myapp.service.StripePaymentService;
import com.stripe.model.PaymentIntent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Stripe Payment APIs")
public class PaymentController {

    private final StripePaymentService stripePaymentService;

    @Operation(summary = "Create a Stripe payment intent")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    @PostMapping("/create-intent")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(@RequestBody CreatePaymentIntentRequest request) {
        return ResponseEntity.ok(stripePaymentService.createPaymentIntent(request));
    }

    @Operation(summary = "Confirm a Stripe payment")
    @ApiResponse(responseCode = "200", description = "Payment confirmed")
    @PostMapping("/confirm")
    public ResponseEntity<PaymentConfirmationResponse> confirmPayment(@RequestBody ConfirmPaymentRequest request) {
        PaymentIntent paymentIntent = stripePaymentService.confirmPayment(request.getPaymentIntentId());

        PaymentConfirmationResponse response = new PaymentConfirmationResponse(
                paymentIntent.getId(),
                paymentIntent.getStatus(),
                paymentIntent.getClientSecret()
        );

        return ResponseEntity.ok(response);
    }

}
