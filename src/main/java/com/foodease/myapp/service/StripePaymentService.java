package com.foodease.myapp.service;

import com.foodease.myapp.domain.payment.CreatePaymentIntentRequest;
import com.foodease.myapp.domain.payment.PaymentIntentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripePaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntentResponse createPaymentIntent(CreatePaymentIntentRequest request) {
        try {
            if (request.getPaymentMethodId() == null || !request.getPaymentMethodId().startsWith("pm_")) {
                throw new IllegalArgumentException("Payment method ID is required.");
            }

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(request.getAmount())
                    .setCurrency(request.getCurrency())
                    .setPaymentMethod(request.getPaymentMethodId())
                    .addPaymentMethodType("card")
                    .putMetadata("orderId", request.getOrderId())
                    .putMetadata("customerEmail", request.getCustomerEmail())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);


            System.out.println("PaymentIntent ID: " + paymentIntent.getId());
            System.out.println("Client secret: " + paymentIntent.getClientSecret());


            return new PaymentIntentResponse(
                    paymentIntent.getClientSecret(),
                    paymentIntent.getId()
            );

        } catch (StripeException e) {
            log.error("Stripe error creating payment intent", e);
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    public PaymentIntent confirmPayment(String paymentIntentId) {
        try {
            if (paymentIntentId == null || !paymentIntentId.startsWith("pi_")) {
                throw new IllegalArgumentException("Invalid payment intent ID.");
            }

            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            log.info("Current payment intent status: {}", paymentIntent.getStatus());

            if (!"succeeded".equalsIgnoreCase(paymentIntent.getStatus())) {
                PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder().build();
                paymentIntent = paymentIntent.confirm(params);
                log.info("PaymentIntent confirmed: {}, status: {}", paymentIntent.getId(), paymentIntent.getStatus());
            }

            return paymentIntent;

        } catch (StripeException e) {
            log.error("Stripe error confirming payment", e);
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

}
