package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.TransactionResponseDto;
import com.cts.OnePay.transaction.dto.TransferRequestDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    public TransactionResponseDto transferMoney(Long senderUserId, TransferRequestDto requestDto);
    public List<TransactionResponseDto> getMyTransactions(Long userId);
    public TransactionResponseDto getMyTransactionById(Long transactionId, Long userId);
    public List<TransactionResponseDto> getAllTransactions();
}
