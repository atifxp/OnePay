package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByUser_UserId(Long userId);
    Optional<Wallet> findByUser_UserId(Long userId);
}
