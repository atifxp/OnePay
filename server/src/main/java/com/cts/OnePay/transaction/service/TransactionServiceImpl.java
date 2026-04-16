package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    public boolean transferMoney(Long senderUserId, Long receiverUserId, BigDecimal amount, String message){
        return true;
    }

    public List<TransactionDto> getMyTransactions(Long userId){
        ArrayList<TransactionDto> transactions= new ArrayList<>();
        return transactions;
    }

    public TransactionDto getMyTransactionById(Long transactionId){
        return new TransactionDto();
    }

    public List<TransactionDto> getAllTransactions(){
        ArrayList<TransactionDto> transactions= new ArrayList<>();
        return transactions;
    }
}
