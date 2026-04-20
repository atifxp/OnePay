package com.cts.OnePay.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletSummaryDto {
    private Long   walletId;
    private Long   userId;
    private String userFullName;
}