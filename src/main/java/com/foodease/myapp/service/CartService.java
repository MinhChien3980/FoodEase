package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.*;
import com.foodease.myapp.service.dto.response.*;
import com.foodease.myapp.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepo;
    CartMapper cartMapper;

    @Transactional
    public CartResponse upsertCart(CartRequest req) {
        Cart cart = cartRepo.findByUserId(req.getUserId())
                .orElseGet(() -> Cart.builder().
                        userId(req.getUserId())
                        .createdAt(LocalDateTime.now())
                        .build());

        cart.getItems().clear();
        cart.getItems().addAll(req.getItems().stream().map(i ->
                CartItem.builder()
                        .cart(cart)
                        .menuItemId(i.getMenuItemId())
                        .quantity(i.getQuantity())
                        .build()
        ).toList());

        Cart saved = cartRepo.save(cart);
        return cartMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        return cartRepo.findByUserId(userId)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user: " + userId));
    }

    @Transactional
    public CartResponse createCart(Long userId) {
        if (cartRepo.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("Cart already exists for user: " + userId);
        }

        Cart cart = Cart.builder()
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();

        Cart saved = cartRepo.save(cart);
        return cartMapper.toDto(saved);
    }


}
