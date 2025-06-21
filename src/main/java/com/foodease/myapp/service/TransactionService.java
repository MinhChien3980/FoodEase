package com.foodease.myapp.service;

import com.foodease.myapp.domain.Order;
import com.foodease.myapp.domain.Transaction;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.repository.OrderRepository;
import com.foodease.myapp.repository.TransactionRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.TransactionRequest;
import com.foodease.myapp.service.dto.response.TransactionResponse;
import com.foodease.myapp.service.mapper.TransactionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repo;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final TransactionMapper mapper;

    @Transactional(readOnly = true)
    public List<TransactionResponse> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionResponse findOne(Long id) {
        Transaction transaction = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found: " + id));
        return mapper.toDto(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findByOrderId(Long orderId) {
        return repo.findByOrderId(orderId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> findByStatus(String status) {
        return repo.findByStatus(status).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponse create(TransactionRequest dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));

        Order order = null;
        if (dto.getOrderId() != null) {
            order = orderRepo.findById(dto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found: " + dto.getOrderId()));
        }

        Transaction transaction = mapper.toEntity(dto);
        transaction.setUser(user);
        transaction.setOrder(order);

        return mapper.toDto(repo.save(transaction));
    }

    @Transactional
    public TransactionResponse update(Long id, TransactionRequest dto) {
        Transaction transaction = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found: " + id));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));

        Order order = null;
        if (dto.getOrderId() != null) {
            order = orderRepo.findById(dto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found: " + dto.getOrderId()));
        }

        mapper.updateFromDto(dto, transaction);
        transaction.setUser(user);
        transaction.setOrder(order);

        return mapper.toDto(repo.save(transaction));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found: " + id);
        }
        repo.deleteById(id);
    }
} 