package com.cts.OnePay.transaction.controller;

import com.cts.OnePay.transaction.dto.TransactionResponseDto;
import com.cts.OnePay.transaction.dto.TransferRequestDto;
import com.cts.OnePay.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody @Validated TransferRequestDto requestDto){
        // TODO: Extract userId from security context
        Long userId= 1L;
        TransactionResponseDto responseDto= transactionService.transferMoney(userId,requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<Page<TransactionResponseDto>>  getMyTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        // TODO: Extract userId from security context
        Long userId= 1L;
        Page<TransactionResponseDto> transactions= transactionService.getMyTransactions(userId,page,size);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDto>  getMyTransactions(@PathVariable Long transactionId){
        // TODO: Extract userId from security context
        Long userId= 1L;
        TransactionResponseDto transaction= transactionService.getMyTransactionById(transactionId,userId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<TransactionResponseDto>>  getAllTransactions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        // TODO: Make sure only admin can access
        Page<TransactionResponseDto> transactions= transactionService.getAllTransactions(page,size);
        return ResponseEntity.ok(transactions);
    }
}
