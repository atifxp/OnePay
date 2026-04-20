package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.model.enums.TransactionStatus;
import com.cts.OnePay.user.model.User;
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
public class TransactionResponseDto {
    private Long transactionId;
    private WalletSummaryDto senderWallet;
    private WalletSummaryDto receiverWallet;
    private BigDecimal amount;
    private TransactionStatus transactionStatus= TransactionStatus.PENDING;
    private String message;
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
}
