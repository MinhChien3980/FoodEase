package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.OrderItem;
import com.foodease.myapp.service.dto.request.OrderItemRequest;
import com.foodease.myapp.service.dto.response.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequest dto);

    @Mapping(target = "orderId", source = "order.id")
    OrderItemResponse toDto(OrderItem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateFromDto(OrderItemRequest dto, @MappingTarget OrderItem entity);
} 