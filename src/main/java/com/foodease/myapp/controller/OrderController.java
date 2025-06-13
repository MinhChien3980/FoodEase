package com.foodease.myapp.controller;

import com.foodease.myapp.service.*;
import com.foodease.myapp.service.dto.request.*;
import com.foodease.myapp.service.dto.response.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Place and list orders")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> place(@RequestBody OrderRequest req) {
        OrderResponse placed = orderService.placeOrder(req);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .data(placed)
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<OrderResponse>> list(@PathVariable Long userId) {
        List<OrderResponse> list = orderService.listOrders(userId);
        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .data(list)
                .build();
    }
}
