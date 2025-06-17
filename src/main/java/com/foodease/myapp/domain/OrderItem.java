package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="menu_item_id", nullable = false)
    private Long menuItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity;
    private Long price;
}
