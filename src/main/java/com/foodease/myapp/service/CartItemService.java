package com.foodease.myapp.service;

import com.foodease.myapp.domain.*;
import com.foodease.myapp.repository.*;
import com.foodease.myapp.service.dto.request.CartItemRequest;
import com.foodease.myapp.service.dto.response.CartItemResponse;
import com.foodease.myapp.service.mapper.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository repo;
    private final CartRepository cartRepo;
    private final CartItemMapper mapper;

    @Transactional(readOnly=true)
    public List<CartItemResponse> findByCartId(Long cartId) {
        return repo.findByCartId(cartId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<CartItemResponse> findByMenuItemId(Long menuItemId) {
        return repo.findByMenuItemId(menuItemId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public CartItemResponse findOne(Long id) {
        CartItem e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found: " + id));
        return mapper.toDto(e);
    }

    @Transactional
    public CartItemResponse create(CartItemRequest dto) {
        Cart cart = cartRepo.findById(dto.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found: " + dto.getCartId()));
        CartItem e = mapper.toEntity(dto);
        e.setCart(cart);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public CartItemResponse update(Long id, CartItemRequest dto) {
        CartItem e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found: " + id));
        Cart cart = cartRepo.findById(dto.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found: " + dto.getCartId()));
        mapper.updateFromDto(dto, e);
        e.setCart(cart);
        return mapper.toDto(repo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByCartId(Long cartId) {
        repo.deleteByCartId(cartId);
    }
} 