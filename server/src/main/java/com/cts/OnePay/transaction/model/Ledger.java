package com.cts.OnePay.transaction.model;

import com.cts.OnePay.audit.Auditable;
import com.cts.OnePay.transaction.model.enums.EntryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ledger")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Ledger extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LedgerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walletId", referencedColumnName = "walletId")
    @NotNull
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionId", referencedColumnName = "transactionId")
    @NotNull
    private Transaction transaction;

    @NotNull
    private EntryType entryType;

    @NotNull
    @Min(value = 1, message = "Transaction amount must be at least 1")
    private BigDecimal amount;

    @NotNull
    @Min(value = 0, message = "Balance cannot be less than 0")
    private BigDecimal balanceBefore;

    @NotNull
    @Min(value = 0, message = "Balance cannot be less than 0")
    private BigDecimal balanceAfter;
}
