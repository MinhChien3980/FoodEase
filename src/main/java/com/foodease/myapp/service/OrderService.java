package com.foodease.myapp.service;

import com.foodease.myapp.domain.Order;
import com.foodease.myapp.domain.OrderItem;
import com.foodease.myapp.repository.OrderRepository;
import com.foodease.myapp.repository.MenuItemRepository;
import com.foodease.myapp.service.dto.request.OrderRequest;
import com.foodease.myapp.service.dto.response.OrderResponse;
import com.foodease.myapp.service.dto.response.OrderItemResponse;
import com.foodease.myapp.service.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepo;
    OrderMapper orderMapper;
    MenuItemRepository menuItemRepo;

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {
        Order order = Order.builder()
                .userId(req.getUserId())
                .totalPrice(req.getTotalPrice())
                .activeStatus(req.getActiveStatus())
                .createdAt(req.getCreatedAt())
                .paymentMethod(req.getPaymentMethod())
                .build();

        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        // Get all menu items to get their prices
        Map<Long, Long> menuItemPrices = menuItemRepo.findAllById(
                req.getItems().stream().map(i -> i.getMenuItemId()).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(
                item -> item.getId(),
                item -> item.getPrice()
        ));

        order.getItems().addAll(req.getItems().stream().map(i ->
                OrderItem.builder()
                        .order(order)
                        .menuItemId(i.getMenuItemId())
                        .quantity(i.getQuantity())
                        .price(menuItemPrices.get(i.getMenuItemId()))
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

    public List<OrderResponse> listAllOrders() {
        return orderRepo.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> getOrderItems(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Order not found: " + orderId));
        
        Map<Long, String> menuItemNames = menuItemRepo.findAllById(
                order.getItems().stream().map(OrderItem::getMenuItemId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(
                item -> item.getId(),
                item -> item.getName()
        ));

        return order.getItems().stream()
                .map(item -> {
                    OrderItemResponse response = OrderItemResponse.builder()
                            .id(item.getId())
                            .orderId(orderId)
                            .menuItemId(item.getMenuItemId())
                            .menuItemName(menuItemNames.get(item.getMenuItemId()))
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();
                    return response;
                })
                .collect(Collectors.toList());
    }
}
