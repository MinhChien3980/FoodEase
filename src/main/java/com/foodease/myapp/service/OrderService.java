package com.foodease.myapp.service;

import com.foodease.myapp.domain.Order;
import com.foodease.myapp.domain.OrderItem;
import com.foodease.myapp.repository.OrderRepository;
import com.foodease.myapp.service.dto.request.OrderRequest;
import com.foodease.myapp.service.dto.response.OrderResponse;
import com.foodease.myapp.service.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepo;
    OrderMapper orderMapper;

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {
        Order order = Order.builder()
                .userId(req.getUserId())
                .totalPrice(req.getTotalPrice())
                .activeStatus(req.getActiveStatus())
                .build();

        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        order.getItems().addAll(req.getItems().stream().map(i ->
                OrderItem.builder()
                        .order(order)
                        .menuItemId(i.getMenuItemId())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build()
        ).toList());

        Order saved = orderRepo.save(order);
        return orderMapper.toDto(saved);
    }

    public List<OrderResponse> listOrders(Long userId) {
        return orderRepo.findAllByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
