package com.foodease.myapp.service;

import com.foodease.myapp.domain.Delivery;
import com.foodease.myapp.domain.Order;
import com.foodease.myapp.repository.DeliveryRepository;
import com.foodease.myapp.repository.OrderRepository;
import com.foodease.myapp.service.dto.request.DeliveryRequest;
import com.foodease.myapp.service.dto.response.DeliveryResponse;
import com.foodease.myapp.service.mapper.DeliveryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository repo;
    private final OrderRepository orderRepo;
    private final DeliveryMapper mapper;

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeliveryResponse findOne(Long id) {
        Delivery delivery = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found: " + id));
        return mapper.toDto(delivery);
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findByOrderId(Long orderId) {
        return repo.findByOrderId(orderId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DeliveryResponse> findByStatus(String status) {
        return repo.findByStatus(status).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryResponse create(DeliveryRequest dto) {
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + dto.getOrderId()));

        Delivery delivery = mapper.toEntity(dto);
        delivery.setOrder(order);
        return mapper.toDto(repo.save(delivery));
    }

    @Transactional
    public DeliveryResponse update(Long id, DeliveryRequest dto) {
        Delivery delivery = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found: " + id));
        
        mapper.updateEntity(dto, delivery);
        return mapper.toDto(repo.save(delivery));
    }

    @Transactional
    public DeliveryResponse updateStatus(Long id, String status) {
        Delivery delivery = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found: " + id));

        delivery.setStatus(status);
        delivery.setDeliveryTime(LocalDateTime.now());
        
        // Update order's active_status based on delivery status
        Order order = delivery.getOrder();
        switch (status) {
            case "PENDING":
                order.setActiveStatus("PENDING");
                break;
            case "CONFIRMED":
                order.setActiveStatus("CONFIRMED");
                break;
            case "PREPARING":
                order.setActiveStatus("PREPARING");
                break;
            case "READY":
                order.setActiveStatus("READY");
                break;
            case "DELIVERING":
                order.setActiveStatus("DELIVERING");
                break;
            case "COMPLETED":
                order.setActiveStatus("COMPLETED");
                break;
            case "CANCELLED":
                order.setActiveStatus("CANCELLED");
                break;
            default:
                // Keep existing status if not matching any case
                break;
        }
        orderRepo.save(order);
        
        return mapper.toDto(repo.save(delivery));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Delivery not found: " + id);
        }
        repo.deleteById(id);
    }
} 