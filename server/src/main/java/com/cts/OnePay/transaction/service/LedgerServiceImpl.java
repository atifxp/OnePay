package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.LedgerDto;
import com.cts.OnePay.transaction.model.Transaction;
import com.cts.OnePay.transaction.model.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerServiceImpl implements LedgerService {
    @Override
    public void record(Wallet senderWallet, Wallet receiverWallet, Transaction transaction, BigDecimal amount, BigDecimal senderBalanceBefore, BigDecimal balance, BigDecimal receiverBalanceBefore, BigDecimal balance1) {

    }

    public List<LedgerDto> getMyPassbook(Long userId){
        ArrayList<LedgerDto> ledgers= new ArrayList<>();
        return ledgers;
    }
    public LedgerDto getLedgerById(Long ledgerId){
        return new LedgerDto();
    }
}
