package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.CartItem;
import com.foodease.myapp.service.dto.request.CartItemRequest;
import com.foodease.myapp.service.dto.response.CartItemResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source="cartId", target="cart.id")
    CartItem toEntity(CartItemRequest dto);

    @Mapping(source="cart.id", target="cartId")
    CartItemResponse toDto(CartItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore=true)
    @Mapping(source="cartId", target="cart.id")
    void updateFromDto(CartItemRequest dto, @MappingTarget CartItem entity);
} 