package com.foodease.myapp.controller;

import com.foodease.myapp.service.SliderImageService;
import com.foodease.myapp.service.dto.request.SliderImageRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.SliderImageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slider-images")
@Tag(name = "SliderImages", description = "Manage homepage slider images")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SliderImageController {
    SliderImageService service;

    @GetMapping
    public ApiResponse<List<SliderImageResponse>> list() {
        return ApiResponse.<List<SliderImageResponse>>builder()
                .code(200)
                .data(service.getAll())
                .build();
    }

    @PostMapping
    public ApiResponse<SliderImageResponse> create(
            @RequestBody @Valid SliderImageRequest req
    ) {
        return ApiResponse.<SliderImageResponse>builder()
                .code(201)
                .data(service.create(req))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SliderImageResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid SliderImageRequest req
    ) {
        return ApiResponse.<SliderImageResponse>builder()
                .code(200)
                .data(service.update(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder()
                .code(204)
                .message("Deleted successfully")
                .build();
    }
}

