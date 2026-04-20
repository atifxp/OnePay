package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.TransactionResponseDto;
import com.cts.OnePay.transaction.dto.TransferRequestDto;
import org.springframework.data.domain.Page;

public interface TransactionService {
    public TransactionResponseDto transferMoney(Long senderUserId, TransferRequestDto requestDto);
    public Page<TransactionResponseDto> getMyTransactions(Long userId, int page, int size);
    public TransactionResponseDto getMyTransactionById(Long transactionId, Long userId);
    public Page<TransactionResponseDto> getAllTransactions(int page, int size);
}
