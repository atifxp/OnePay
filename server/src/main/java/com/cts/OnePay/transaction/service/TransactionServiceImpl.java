package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.TransactionResponseDto;
import com.cts.OnePay.transaction.dto.TransferRequestDto;
import com.cts.OnePay.transaction.dto.WalletSummaryDto;
import com.cts.OnePay.transaction.model.Transaction;
import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.model.enums.TransactionStatus;
import com.cts.OnePay.transaction.repository.TransactionRepository;
import com.cts.OnePay.transaction.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final LedgerService ledgerService;
    private final ModelMapper modelMapper;

    @Transactional
    public TransactionResponseDto transferMoney(Long senderUserId, TransferRequestDto requestDto){
        LocalDateTime initiationTime= LocalDateTime.now();

        // Fetch user wallet and validations
        Wallet senderWallet= walletRepository.findByUser_UserId(senderUserId)
                .orElseThrow(()-> new RuntimeException("User wallet not found"));
        Wallet receiverWallet= walletRepository.findByUser_UserId(requestDto.getReceiverUserId())
                .orElseThrow(()-> new RuntimeException("Receiver wallet not found"));

        if(senderWallet.getWalletId().equals(receiverWallet.getWalletId())){
            throw new IllegalArgumentException("Cannot transfer to your own wallet");
        }
        if(senderWallet.getBalance().compareTo(requestDto.getAmount()) <0){
            throw new IllegalArgumentException("You don't have sufficient balance");
        }
        // Update user wallets
        BigDecimal senderBalanceBefore= senderWallet.getBalance();
        BigDecimal receiverBalanceBefore= receiverWallet.getBalance();
        senderWallet.setBalance(senderBalanceBefore.subtract(requestDto.getAmount()));
        receiverWallet.setBalance(receiverBalanceBefore.add(requestDto.getAmount()));
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // Create Transaction entry
        Transaction transaction= new Transaction();
        transaction.setAmount(requestDto.getAmount());
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setInitiatedAt(initiationTime);
        transaction.setCompletedAt(LocalDateTime.now());
        transaction.setMessage(requestDto.getMessage());
        transactionRepository.save(transaction);

        // Store in ledger for both user
        ledgerService.record(
                senderWallet,   receiverWallet,  transaction,
                requestDto.getAmount(),
                senderBalanceBefore,   senderWallet.getBalance(),
                receiverBalanceBefore, receiverWallet.getBalance()
        );

        // Map data into response dto
        return toDto(transaction);
    }

    public Page<TransactionResponseDto> getMyTransactions(Long userId, int page, int size){
        Wallet wallet= walletRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new RuntimeException("Cannot find user wallet"));
        Long walletId= wallet.getWalletId();
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC, "completedAt");
        Page<Transaction> transactions=transactionRepository
                .findBySenderWallet_WalletIdOrReceiverWallet_WalletId(walletId, walletId, pageable);
        return transactions.map(this::toDto);
    }

    public TransactionResponseDto getMyTransactionById(Long transactionId, Long userId){
        Transaction transaction= transactionRepository.findById(transactionId)
                .orElseThrow(()->new RuntimeException("Transaction doesn't exist"));
        Wallet wallet= walletRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new RuntimeException("Wallet not found"));

        // check if transaction belongs to user
        boolean isValid= transaction.getSenderWallet().getWalletId().equals(wallet.getWalletId())
                || transaction.getReceiverWallet().getWalletId().equals(wallet.getWalletId());
        if(!isValid){
            throw new RuntimeException("You don't have access to this transaction");
        }

        return toDto(transaction);
    }

    public Page<TransactionResponseDto> getAllTransactions(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Transaction> transactions=transactionRepository.findAll(pageable);
        return transactions.map(txn->toDto(txn));
    }

    private TransactionResponseDto toDto(Transaction transaction){
        TransactionResponseDto transactionResponseDto= modelMapper.map(transaction,TransactionResponseDto.class);
        WalletSummaryDto senderSummary = new WalletSummaryDto();
        senderSummary.setWalletId(transaction.getSenderWallet().getWalletId());
        senderSummary.setUserId(transaction.getSenderWallet().getUser().getUserId());
        senderSummary.setUserFullName(transaction.getSenderWallet().getUser().getFullName());
        WalletSummaryDto receiverSummary = new WalletSummaryDto();
        receiverSummary.setWalletId(transaction.getReceiverWallet().getWalletId());
        receiverSummary.setUserId(transaction.getReceiverWallet().getUser().getUserId());
        receiverSummary.setUserFullName(transaction.getReceiverWallet().getUser().getFullName());

        transactionResponseDto.setSenderWallet(senderSummary);
        transactionResponseDto.setReceiverWallet(receiverSummary);

        return transactionResponseDto;
    }
}
