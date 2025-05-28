package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.*;
import com.foodease.myapp.service.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;

    @Transactional
    public CartResponse upsertCart(CartRequest req) {
        Cart cart = cartRepo.findByUserId(req.getUserId())
                .orElseGet(() -> Cart.builder().
                        userId(req.getUserId())
                        .createdAt(req.getCreatedAt())
                        .build());

        // replace all items
        cart.getItems().clear();
        cart.getItems().addAll(req.getItems().stream().map(i ->
                CartItem.builder()
                        .cart(cart)
                        .menuItemId(i.getMenuItemId())
                        .quantity(i.getQuantity())
                        .build()
        ).collect(Collectors.toList()));

        Cart saved = cartRepo.save(cart);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        return cartRepo.findByUserId(userId)
                .map(this::toDto)
                .orElse(CartResponse.builder()
                        .userId(userId)
                        .items(List.of())
                        .build());
    }

    private CartResponse toDto(Cart c) {
        return CartResponse.builder()
                .id(c.getId())
                .userId(c.getUserId())
                .createdAt(c.getCreatedAt())
                .items(c.getItems().stream().map(ci ->
                        CartResponse.Item.builder()
                                .id(ci.getId())
                                .menuItemId(ci.getMenuItemId())
                                .quantity(ci.getQuantity())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
