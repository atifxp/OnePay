package com.cts.OnePay.transaction.controller;

import com.cts.OnePay.transaction.dto.CreateWalletRequestDto;
import com.cts.OnePay.transaction.dto.WalletResponseDto;
import com.cts.OnePay.transaction.service.WalletService;
import com.cts.OnePay.user.model.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<WalletResponseDto> createWallet(@RequestBody @Validated CreateWalletRequestDto requestDto){
        WalletResponseDto response = walletService.createUserWallet(requestDto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<WalletResponseDto> getUserWallet(@AuthenticationPrincipal MyUserDetails userDetails){
        WalletResponseDto responseDto= walletService.getUserWallet(userDetails.getUserId());
        return ResponseEntity.ok(responseDto);
    }
}
