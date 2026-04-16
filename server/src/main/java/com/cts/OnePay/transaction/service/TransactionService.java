package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface TransactionService {
    public boolean transferMoney(Long senderUserId, Long receiverUserId, BigDecimal amount, String message);
    public List<TransactionDto> getMyTransactions(Long userId);
    public TransactionDto getMyTransactionById(Long transactionId);
    public List<TransactionDto> getAllTransactions();
}
