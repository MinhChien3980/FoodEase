package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Delivery;
import com.foodease.myapp.service.dto.request.DeliveryRequest;
import com.foodease.myapp.service.dto.response.DeliveryResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DeliveryMapper {
    
    public DeliveryResponse toDto(Delivery entity) {
        if (entity == null) {
            return null;
        }
        
        return DeliveryResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .status(entity.getStatus())
                .deliveryTime(entity.getDeliveryTime())
                .build();
    }
    
    public Delivery toEntity(DeliveryRequest dto) {
        if (dto == null) {
            return null;
        }
        
        Delivery entity = new Delivery();
        entity.setStatus(dto.getStatus());
        entity.setDeliveryTime(dto.getDeliveryTime() != null ? dto.getDeliveryTime() : LocalDateTime.now());
        return entity;
    }
    
    public void updateEntity(DeliveryRequest dto, Delivery entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setStatus(dto.getStatus());
        if (dto.getDeliveryTime() != null) {
            entity.setDeliveryTime(dto.getDeliveryTime());
        }
    }
} 