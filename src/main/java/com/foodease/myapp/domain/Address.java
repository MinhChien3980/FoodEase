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
@Table(name = "addresses")
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "address_line", columnDefinition = "text", nullable = false)
    private String addressLine;

    private String area;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String type = "Home";
    private String contactName;
    private String mobile;
    private String countryCode;
    private String alternateMobile;
    private String landmark;
    private String pincode;
    private String state;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
