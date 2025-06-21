package com.foodease.myapp.service;

import com.foodease.myapp.domain.Order;
import com.foodease.myapp.domain.OrderItem;
import com.foodease.myapp.repository.OrderItemRepository;
import com.foodease.myapp.repository.OrderRepository;
import com.foodease.myapp.repository.MenuItemRepository;
import com.foodease.myapp.service.dto.request.OrderItemRequest;
import com.foodease.myapp.service.dto.response.OrderItemResponse;
import com.foodease.myapp.service.mapper.OrderItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderItemService {
    OrderItemRepository repo;
    OrderRepository orderRepo;
    MenuItemRepository menuItemRepo;
    OrderItemMapper mapper;

    @Transactional(readOnly = true)
    public List<OrderItemResponse> findByOrderId(Long orderId) {
        List<OrderItem> items = repo.findByOrderId(orderId);
        Map<Long, String> menuItemNames = menuItemRepo.findAllById(
                items.stream().map(OrderItem::getMenuItemId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(
                item -> item.getId(),
                item -> item.getName()
        ));

        return items.stream()
                .map(item -> {
                    OrderItemResponse response = mapper.toDto(item);
                    response.setMenuItemName(menuItemNames.get(item.getMenuItemId()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderItemResponse findOne(Long id) {
        OrderItem item = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found: " + id));
        OrderItemResponse response = mapper.toDto(item);
        response.setMenuItemName(menuItemRepo.findById(item.getMenuItemId())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found: " + item.getMenuItemId()))
                .getName());
        return response;
    }

    @Transactional
    public OrderItemResponse create(OrderItemRequest dto) {
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + dto.getOrderId()));
        OrderItem item = mapper.toEntity(dto);
        item.setOrder(order);
        OrderItem saved = repo.save(item);
        OrderItemResponse response = mapper.toDto(saved);
        response.setMenuItemName(menuItemRepo.findById(saved.getMenuItemId())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found: " + saved.getMenuItemId()))
                .getName());
        return response;
    }

    @Transactional
    public OrderItemResponse update(Long id, OrderItemRequest dto) {
        OrderItem item = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found: " + id));
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + dto.getOrderId()));
        mapper.updateFromDto(dto, item);
        item.setOrder(order);
        OrderItem saved = repo.save(item);
        OrderItemResponse response = mapper.toDto(saved);
        response.setMenuItemName(menuItemRepo.findById(saved.getMenuItemId())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found: " + saved.getMenuItemId()))
                .getName());
        return response;
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
} 