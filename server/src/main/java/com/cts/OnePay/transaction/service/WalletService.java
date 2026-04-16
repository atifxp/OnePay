package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.WalletDto;

public interface WalletService {
    public boolean createUserWallet(Long UserId);
    public WalletDto getUserWallet(Long userId);
}
