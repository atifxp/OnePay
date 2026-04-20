package com.cts.OnePay.transaction.repository;

import com.cts.OnePay.transaction.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger,Long> {
}
