package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.UserLoginRequestDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> register(User user){
        return new HashMap<>();
    }

    public Map<String, Object> login(UserLoginRequestDto user){
        return new HashMap<>();
    }

    public Map<String, Object> refresh(String accessToken){
        return new HashMap<>();
    }

    public void logout(Long userId){

    }

}
