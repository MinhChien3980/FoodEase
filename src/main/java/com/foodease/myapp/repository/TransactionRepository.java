package com.foodease.myapp.repository;

import com.foodease.myapp.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByOrderId(Long orderId);
    List<Transaction> findByStatus(String status);
    List<Transaction> findByTransactionType(String transactionType);
} 