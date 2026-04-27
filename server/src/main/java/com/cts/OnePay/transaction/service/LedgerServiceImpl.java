package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.LedgerDto;
import com.cts.OnePay.transaction.model.Ledger;
import com.cts.OnePay.transaction.model.Transaction;
import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.model.enums.EntryType;
import com.cts.OnePay.transaction.repository.LedgerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    public void record(Wallet senderWallet, Wallet receiverWallet, Transaction transaction, BigDecimal amount, BigDecimal senderBefore,   BigDecimal senderAfter,
                       BigDecimal receiverBefore, BigDecimal receiverAfter) {
        Ledger credit= new Ledger();
        credit.setAmount(amount);
        credit.setWallet(receiverWallet);
        credit.setTransaction(transaction);
        credit.setEntryType(EntryType.CREDIT);
        credit.setBalanceBefore(receiverBefore);
        credit.setBalanceAfter(receiverAfter);

        Ledger debit= new Ledger();
        debit.setAmount(amount);
        debit.setWallet(senderWallet);
        debit.setTransaction(transaction);
        debit.setEntryType(EntryType.DEBIT);
        debit.setBalanceBefore(senderBefore);
        debit.setBalanceAfter(senderAfter);

        ledgerRepository.save(credit);
        ledgerRepository.save(debit);
    }

    public List<LedgerDto> getMyPassbook(Long userId){
        ArrayList<LedgerDto> ledgers= new ArrayList<>();
        return ledgers;
    }
    public LedgerDto getLedgerById(Long ledgerId){
        return new LedgerDto();
    }
}
