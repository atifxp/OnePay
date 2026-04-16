package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto {
    private Long transactionId;
    private Wallet senderWallet;
    private Wallet receiverWallet;
    private BigDecimal amount;
    private TransactionStatus transactionStatus= TransactionStatus.PENDING;
    private String message;
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
}
