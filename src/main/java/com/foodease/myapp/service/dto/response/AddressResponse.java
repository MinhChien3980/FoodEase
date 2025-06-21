package com.foodease.myapp.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AddressResponse {
    private Long id;
    private Long userId;
    private String addressLine;
    private String area;
    private Long cityId;
    private String cityName;
    private String type;
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
    private Boolean isDefault;
    private LocalDateTime createdAt;
} 