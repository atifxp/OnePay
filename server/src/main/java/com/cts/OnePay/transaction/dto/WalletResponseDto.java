package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.user.model.User;
import jakarta.validation.constraints.Min;
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
public class WalletResponseDto {
    private Long walletId;
    @NotNull
    private User user;
    @Min(value = 0, message = "Balance cannot be less than 0")
    private BigDecimal balance= BigDecimal.ZERO;
    private String currency= "INR";
}
