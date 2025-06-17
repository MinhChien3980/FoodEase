package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "restaurants")
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String address;

    private String phone;
    
    private String email;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "rating")
    private BigDecimal rating;
    
    @Column(name = "delivery_time")
    private Integer deliveryTime; // in minutes
    
    @Column(name = "is_open")
    private Boolean isOpen = (Boolean) true;
    
    @Column(name = "cuisine_types")
    private String cuisineTypes; // JSON string for multiple cuisines
    
    @Column(name = "min_price")
    private Long minPrice;
    
    @Column(name = "max_price")
    private Long maxPrice;
    
    @Column(name = "latitude")
    private BigDecimal latitude;
    
    @Column(name = "longitude")
    private BigDecimal longitude;
    
    @Column(name = "opening_hours", columnDefinition = "text")
    private String openingHours; // JSON string for weekly hours

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;
}
