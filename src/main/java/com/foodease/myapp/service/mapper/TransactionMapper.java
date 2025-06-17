package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Transaction;
import com.foodease.myapp.service.dto.request.TransactionRequest;
import com.foodease.myapp.service.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    
    public TransactionResponse toDto(Transaction entity) {
        if (entity == null) {
            return null;
        }
        
        TransactionResponse dto = new TransactionResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setOrderId(entity.getOrder() != null ? entity.getOrder().getId() : null);
        dto.setTransactionType(entity.getTransactionType());
        dto.setType(entity.getType());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setTxnId(entity.getTxnId());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        dto.setMessage(entity.getMessage());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }
    
    public Transaction toEntity(TransactionRequest dto) {
        if (dto == null) {
            return null;
        }
        
        Transaction entity = new Transaction();
        updateFromDto(dto, entity);
        return entity;
    }
    
    public void updateFromDto(TransactionRequest dto, Transaction entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
    }
} 