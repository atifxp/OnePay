package com.cts.OnePay.transaction.service;

import com.cts.OnePay.transaction.dto.CreateWalletRequestDto;
import com.cts.OnePay.transaction.dto.WalletResponseDto;
import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.repository.WalletRepository;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public WalletResponseDto createUserWallet(Long userId){
        if(walletRepository.existsByUser_UserId(userId)){
            throw new WalletAlreadyExistsException("Wallet already exists for user");
        }

        User user= userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setUser(user);

        WalletResponseDto responseDto= modelMapper.map(wallet, WalletResponseDto.class);
        return responseDto;
    }

    public WalletResponseDto getUserWallet(Long userId){
        Wallet wallet= walletRepository.findByUser_UserId(userId)
                .orElseThrow(()->new RuntimeException("Wallet not found"));
        WalletResponseDto responseDto= modelMapper.map(wallet,WalletResponseDto.class);
        return responseDto;
    }
}
