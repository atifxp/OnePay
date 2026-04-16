package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
