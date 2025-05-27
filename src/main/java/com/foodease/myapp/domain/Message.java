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
@Table(name = "messages")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_id")
    private Long userId;

    @Column(columnDefinition = "text")
    private String message;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String attachmentsJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

