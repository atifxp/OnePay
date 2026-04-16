package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.LedgerDto;

import java.util.List;

public interface LedgerService {
    public List<LedgerDto> getMyPassbook(Long userId);
    public LedgerDto getLedgerById(Long ledgerId);
}
