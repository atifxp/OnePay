package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
