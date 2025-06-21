package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.*;
import com.foodease.myapp.service.dto.response.*;
import com.foodease.myapp.service.mapper.CartMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    CartItemRepository cartItemRepo;
    MenuItemRepository menuItemRepo;
    UserRepository userRepo;
    CartMapper cartMapper;
    ObjectMapper objectMapper;

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

    @Transactional
    public AddToCartResponse addToCart(AddToCartRequest request) {
        // Get menu item to validate and get price
        MenuItem menuItem = menuItemRepo.findById(request.getMenuItemId())
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + request.getMenuItemId()));
        
        // For now, we'll assume we have the user ID from authentication context
        // In a real application, this would come from the JWT token
        Long userId = getCurrentUserId(); // This needs to be implemented based on your auth system
        
        // Find or create cart for user
        Cart cart = cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return cartRepo.save(newCart);
                });
        
        // Calculate total price
        Long basePrice = menuItem.getPrice();
        Long extraPrice = request.getCustomizations() != null ?
                request.getCustomizations().stream()
                        .mapToLong(c -> c.getExtraPrice() != null ? c.getExtraPrice() : 0L)
                        .sum() : 0L;
        Long totalPrice = (basePrice + extraPrice) * request.getQuantity();
        
        // Convert customizations to JSON
        String customizationsJson = null;
        if (request.getCustomizations() != null && !request.getCustomizations().isEmpty()) {
            try {
                customizationsJson = objectMapper.writeValueAsString(request.getCustomizations());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize customizations", e);
            }
        }
        
        // Create cart item
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .menuItemId(request.getMenuItemId())
                .quantity(request.getQuantity())
                .specialInstructions(request.getSpecialInstructions())
                .customizations(customizationsJson)
                .totalPrice(totalPrice)
                .build();
        
        CartItem saved = cartItemRepo.save(cartItem);
        
        // Convert customizations back for response
        List<AddToCartResponse.Customization> responseCustomizations = null;
        if (request.getCustomizations() != null) {
            responseCustomizations = request.getCustomizations().stream()
                    .map(c -> AddToCartResponse.Customization.builder()
                            .type(c.getType())
                            .value(c.getValue())
                            .extraPrice(c.getExtraPrice())
                            .build())
                    .collect(Collectors.toList());
        }
        
        return AddToCartResponse.builder()
                .cartItemId(saved.getId())
                .menuItemId(saved.getMenuItemId())
                .quantity(saved.getQuantity())
                .totalPrice(saved.getTotalPrice())
                .customizations(responseCustomizations)
                .build();
    }
    
    // This method needs to be implemented based on your authentication system
    private Long getCurrentUserId() {
        // For now, return a dummy user ID
        // In real implementation, extract from JWT token or Spring Security context
        return 1L; // TODO: Implement proper user ID retrieval
    }


}
