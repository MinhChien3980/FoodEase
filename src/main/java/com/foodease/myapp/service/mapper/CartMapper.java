package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Cart;
import com.foodease.myapp.domain.CartItem;
import com.foodease.myapp.service.dto.request.CartRequest;
import com.foodease.myapp.service.dto.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", source = "items")
    Cart toEntity(CartRequest request);

    List<CartItem> toEntity(List<CartRequest.Item> items);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "cart", ignore = true)
    })
    CartItem toEntity(CartRequest.Item item);

    CartResponse toDto(Cart cart);
    List<CartResponse.Item> toDtoItems(List<CartItem> items);

    @Mappings({
            @Mapping(source = "menuItemId", target = "menuItemId"),
            @Mapping(source = "quantity", target = "quantity")
    })
    CartResponse.Item toDto(CartItem item);
}