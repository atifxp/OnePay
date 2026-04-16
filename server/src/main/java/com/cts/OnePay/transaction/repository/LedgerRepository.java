package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<Ledger,Long> {
}
