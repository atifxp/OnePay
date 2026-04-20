package com.cts.OnePay.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferRequestDto {
    @NotNull(message = "Receiver user id must be present")
    private Long receiverUserId;
    @DecimalMin(value = "1.0", message = "Transaction amount must be at least 1")
    private BigDecimal amount;
    private String message;
}
