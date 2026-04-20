package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByUser_UserId(Long userId);
    Optional<Wallet> findByUser_UserId(Long userId);
}
