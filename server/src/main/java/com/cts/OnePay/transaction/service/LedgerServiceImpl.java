package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.LedgerDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerServiceImpl implements LedgerService {
    public List<LedgerDto> getMyPassbook(Long userId){
        ArrayList<LedgerDto> ledgers= new ArrayList<>();
        return ledgers;
    }
    public LedgerDto getLedgerById(Long ledgerId){
        return new LedgerDto();
    }
}
