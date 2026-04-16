package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.WalletDto;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{
    public boolean createUserWallet(Long userId){
        return true;
    }
    public WalletDto getUserWallet(Long userId){
        return new WalletDto();
    }
}
