package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city_deliverability")
public class CityDeliverability {
    @Id
    @Column(name = "city_id")
    private Long cityId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "is_deliverable", nullable = false)
    private Boolean isDeliverable = true;
}
