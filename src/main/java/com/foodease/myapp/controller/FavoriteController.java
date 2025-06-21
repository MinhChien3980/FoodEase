package com.foodease.myapp.controller;

import com.foodease.myapp.service.FavoriteService;
import com.foodease.myapp.service.dto.request.FavoriteRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.ToggleFavoriteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/favorites")
@Tag(name = "Favorites", description = "Add/remove/list user favorites")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favService;

    @PostMapping("/toggle")
    public ApiResponse<ToggleFavoriteResponse> toggleFavorite(@Valid @RequestBody FavoriteRequest request) {
        ToggleFavoriteResponse result = favService.toggleFavorite(request);
        return ApiResponse.<ToggleFavoriteResponse>builder()
                .code(200)
                .message("Favorite status updated")
                .data(result)
                .build();
    }

    @GetMapping
    public ApiResponse<Map<String, Object>> getFavorites() {
        Map<String, Object> favorites = favService.getFavorites();
        return ApiResponse.<Map<String, Object>>builder()
                .code(200)
                .data(favorites)
                .build();
    }
} 