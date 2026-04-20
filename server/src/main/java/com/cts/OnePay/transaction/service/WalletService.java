package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.CreateWalletRequestDto;
import com.cts.OnePay.transaction.dto.WalletResponseDto;

public interface WalletService {
    public WalletResponseDto createUserWallet(Long userId);
    public WalletResponseDto getUserWallet(Long userId);
}
