package com.foodease.myapp.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String login;

    private String email;

    private Boolean activated;

    private String langKey;

    private String createdBy;

    private LocalDateTime createdAt;

    private Set<String> roles;

    // UserProfile thông tin
    private String fullName;
    private String phone;
    private String imageUrl;
    private String referralCode;
    private Integer cityId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}

