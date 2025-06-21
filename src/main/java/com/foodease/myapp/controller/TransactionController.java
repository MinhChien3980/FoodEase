package com.foodease.myapp.controller;

import com.foodease.myapp.service.TransactionService;
import com.foodease.myapp.service.dto.request.TransactionRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class TransactionController {
    private final TransactionService service;

    @GetMapping
    public ApiResponse<List<TransactionResponse>> findAll() {
        return ApiResponse.<List<TransactionResponse>>builder()
                .code(200)
                .data(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TransactionResponse> findOne(@PathVariable Long id) {
        return ApiResponse.<TransactionResponse>builder()
                .code(200)
                .data(service.findOne(id))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<TransactionResponse>> findByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<TransactionResponse>>builder()
                .code(200)
                .data(service.findByUserId(userId))
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<TransactionResponse>> findByOrderId(@PathVariable Long orderId) {
        return ApiResponse.<List<TransactionResponse>>builder()
                .code(200)
                .data(service.findByOrderId(orderId))
                .build();
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<TransactionResponse>> findByStatus(@PathVariable String status) {
        return ApiResponse.<List<TransactionResponse>>builder()
                .code(200)
                .data(service.findByStatus(status))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TransactionResponse> create(@RequestBody TransactionRequest request) {
        return ApiResponse.<TransactionResponse>builder()
                .code(201)
                .data(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TransactionResponse> update(@PathVariable Long id, @RequestBody TransactionRequest request) {
        return ApiResponse.<TransactionResponse>builder()
                .code(200)
                .data(service.update(id, request))
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