package com.foodease.myapp.controller;

import com.foodease.myapp.service.DeliveryService;
import com.foodease.myapp.service.dto.request.DeliveryRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.DeliveryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@Tag(name = "Delivery", description = "CRUD operations for deliveries")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class DeliveryController {
    DeliveryService service;

    @GetMapping
    public ApiResponse<List<DeliveryResponse>> list() {
        return ApiResponse.<List<DeliveryResponse>>builder()
                .code(200)
                .data(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DeliveryResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<DeliveryResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @GetMapping("/by-order")
    public ApiResponse<List<DeliveryResponse>> getByOrderId(@RequestParam Long orderId) {
        return ApiResponse.<List<DeliveryResponse>>builder()
                .code(200)
                .data(service.findByOrderId(orderId))
                .build();
    }

    @GetMapping("/by-status")
    public ApiResponse<List<DeliveryResponse>> getByStatus(@RequestParam String status) {
        return ApiResponse.<List<DeliveryResponse>>builder()
                .code(200)
                .data(service.findByStatus(status))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DeliveryResponse> create(@Valid @RequestBody DeliveryRequest dto) {
        return ApiResponse.<DeliveryResponse>builder()
                .code(201)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DeliveryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequest dto
    ) {
        return ApiResponse.<DeliveryResponse>builder()
                .code(200)
                .data(service.update(id, dto))
                .build();
    }

    @PutMapping("/{id}/status")
    public ApiResponse<DeliveryResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ApiResponse.<DeliveryResponse>builder()
                .code(200)
                .data(service.updateStatus(id, status))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder()
                .code(204)
                .build();
    }
} 