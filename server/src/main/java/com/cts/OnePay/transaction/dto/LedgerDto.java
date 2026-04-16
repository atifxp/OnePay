package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.transaction.model.Transaction;
import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.model.enums.EntryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LedgerDto {
    private Long LedgerId;
    private Wallet wallet;
    private Transaction transaction;
    private EntryType entryType;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
}
