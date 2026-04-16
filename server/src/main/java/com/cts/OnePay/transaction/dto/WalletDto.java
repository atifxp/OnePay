package com.cts.OnePay.transaction.dto;

import com.cts.OnePay.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDto {
    private Long walletId;
    private User user;
    private BigDecimal balance= BigDecimal.ZERO;
    private String currency= "INR";
}
