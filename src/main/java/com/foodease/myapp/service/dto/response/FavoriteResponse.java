package com.foodease.myapp.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FavoriteResponse {
    private Long id;
    private Long userId;
    private String favoritableType;
    private Long favoritableId;
    private LocalDateTime createdAt;
}
