package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "delivery_charge")
    private BigDecimal deliveryCharge;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage;

    @Column(name = "final_total")
    private BigDecimal finalTotal;

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "active_status")
    private String activeStatus;

    @Column(name = "is_self_pickup")
    private Boolean isSelfPickup = false;

    @Column(name = "order_note", columnDefinition = "text")
    private String orderNote;

    @Column(name = "delivery_tip", columnDefinition = "text")
    private String deliveryTip;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
