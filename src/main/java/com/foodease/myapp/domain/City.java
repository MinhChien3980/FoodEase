package com.foodease.myapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class City {
    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
