package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findBySenderWallet_WalletId(Long walletId, Pageable pageable);
    Page<Transaction> findBySenderWallet_WalletIdOrReceiverWallet_WalletId(Long senderWalletId, Long receiverWalletId, Pageable pageable);
}
