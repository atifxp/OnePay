package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.UserResponseDto;
import com.cts.OnePay.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDto getProfile(Long userId){
        return new UserResponseDto();
    }


    public UserResponseDto updateProfile(){
        return new UserResponseDto();
    }


}
