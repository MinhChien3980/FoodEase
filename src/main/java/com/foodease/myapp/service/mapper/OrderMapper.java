package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Order;
import com.foodease.myapp.domain.OrderItem;
import com.foodease.myapp.service.dto.request.OrderRequest;
import com.foodease.myapp.service.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "deliveryCharge", ignore = true)
    @Mapping(target = "taxAmount", ignore = true)
    @Mapping(target = "taxPercentage", ignore = true)
    @Mapping(target = "finalTotal", ignore = true)
    Order toEntity(OrderRequest request);

    List<OrderItem> toEntity(List<OrderRequest.Item> items);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "order", ignore = true)
    })
    OrderItem toEntity(OrderRequest.Item item);

    OrderResponse toDto(Order order);
    List<OrderResponse.Item> toDtoItems(List<OrderItem> items);

    @Mappings({
            @Mapping(source = "menuItemId", target = "menuItemId"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price")
    })
    OrderResponse.Item toDto(OrderItem item);
}

