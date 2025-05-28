package com.foodease.myapp.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class FavoriteRequest {
    @NotNull
    private Long userId;

    @NotNull
    private String favoritableType;

    @NotNull
    private Long favoritableId;
}
