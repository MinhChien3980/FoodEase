package com.foodease.myapp.controller;

import com.foodease.myapp.service.FavoriteService;
import com.foodease.myapp.service.dto.request.FavoriteRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.FavoriteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@Tag(name="Favorites", description="Add/remove/list user favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FavoriteResponse> add(@RequestBody @Validated FavoriteRequest req) {
        FavoriteResponse added = favService.addFavorite(req);
        return ApiResponse.<FavoriteResponse>builder()
                .code(201)
                .message("Added to favorites")
                .data(added)
                .build();
    }

    @DeleteMapping
    public ApiResponse<Void> remove(
            @Valid @RequestBody FavoriteRequest req
    ) {
        favService.removeFavorite(req);
        return ApiResponse.<Void>builder()
                .code(204)
                .message("Removed from favorites")
                .build();
    }

    @GetMapping
    public ApiResponse<List<FavoriteResponse>> list(
            @RequestParam Long userId,
            @RequestParam String favoritableType
    ) {
        List<FavoriteResponse> list = favService.listFavorites(userId, favoritableType);
        return ApiResponse.<List<FavoriteResponse>>builder()
                .code(200)
                .data(list)
                .build();
    }
}
