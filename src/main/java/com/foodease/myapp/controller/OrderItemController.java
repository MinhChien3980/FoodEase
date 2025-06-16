package com.foodease.myapp.controller;

import com.foodease.myapp.service.OrderItemService;
import com.foodease.myapp.service.dto.request.OrderItemRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.OrderItemResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/order-items")
@Tag(name = "OrderItem", description = "CRUD for order items")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class OrderItemController {
    OrderItemService service;

    @GetMapping
    public ApiResponse<List<OrderItemResponse>> listByOrder(@RequestParam Long orderId) {
        return ApiResponse.<List<OrderItemResponse>>builder()
                .code(200)
                .data(service.findByOrderId(orderId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderItemResponse> getOne(@PathVariable Long id) {
        return ApiResponse.<OrderItemResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderItemResponse> create(@Valid @RequestBody OrderItemRequest dto) {
        return ApiResponse.<OrderItemResponse>builder()
                .code(201)
                .data(service.create(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderItemRequest dto
    ) {
        return ApiResponse.<OrderItemResponse>builder()
                .code(200)
                .data(service.update(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder().code(204).build();
    }
} 