package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cart_items")
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name="menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(nullable=false)
    private Integer quantity;
    
    @Column(name="special_instructions", columnDefinition = "text")
    private String specialInstructions;
    
    @Column(name="customizations", columnDefinition = "text")
    private String customizations; // JSON string for customizations
    
    @Column(name="total_price")
    private Long totalPrice;
}
