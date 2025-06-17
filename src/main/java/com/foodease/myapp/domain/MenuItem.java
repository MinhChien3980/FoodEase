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
@Table(name = "menu_items")
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MenuCategory category;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private Long price; // price in VND as integer

    @Column(name = "original_price")
    private Long originalPrice;
    
    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "is_vegetarian")
    private Boolean isVegetarian = (Boolean) false;
    
    @Column(name = "is_available")
    private Boolean isAvailable = (Boolean) true;
    
    @Column(name = "rating")
    private BigDecimal rating;
    
    @Column(name = "reviews_count")
    private Integer reviewsCount = (Integer) 0;
    
    @Column(name = "preparation_time")
    private Integer preparationTime; // in minutes
    
    @Column(name = "ingredients", columnDefinition = "text")
    private String ingredients; // JSON array
    
    @Column(name = "allergens", columnDefinition = "text")
    private String allergens; // JSON array
    
    @Column(name = "nutritional_info", columnDefinition = "text")
    private String nutritionalInfo; // JSON object
    
    @Column(name = "spice_level")
    private String spiceLevel; // none, mild, medium, hot, very_hot
    
    @Column(name = "tags", columnDefinition = "text")
    private String tags; // JSON array

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MenuItem that = (MenuItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
