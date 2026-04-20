package com.cts.OnePay.transaction.model;

import com.cts.OnePay.transaction.model.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Table(name = "transaction")
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendWalletId", referencedColumnName = "walletId" )
    @NotNull
    private Wallet senderWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverWalletId", referencedColumnName = "walletId" )
    @NotNull
    private Wallet receiverWallet;

    @NotNull
    @Column(precision = 15, scale = 2)
    @DecimalMin(value = "1.0", message = "Transaction amount must be at least 1")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus= TransactionStatus.PENDING;

    @NotNull(message = "Please provide a message for the transaction")
    private String message;

    @NotNull
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
}
