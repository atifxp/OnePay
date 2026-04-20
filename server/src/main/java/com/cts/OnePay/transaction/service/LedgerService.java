package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.LedgerDto;
import com.cts.OnePay.transaction.model.Transaction;
import com.cts.OnePay.transaction.model.Wallet;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerService {
    public List<LedgerDto> getMyPassbook(Long userId);
    public LedgerDto getLedgerById(Long ledgerId);

    void record(Wallet senderWallet, Wallet receiverWallet, Transaction transaction, @DecimalMin(value = "1.0", message = "Transaction amount must be at least 1") BigDecimal amount, BigDecimal senderBalanceBefore, @Min(value = 0, message = "Balance cannot be less than 0") BigDecimal balance, BigDecimal receiverBalanceBefore, @Min(value = 0, message = "Balance cannot be less than 0") BigDecimal balance1);
}
