package com.example.apibank1.repository;

import com.example.apibank1.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}