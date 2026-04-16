package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.UserLoginRequestDto;
import com.cts.OnePay.user.model.User;

import java.util.Map;

public interface AuthService {
    Map<String, Object> register(User user);
    Map<String, Object> login(UserLoginRequestDto user);
    Map<String, Object> refresh(String accessToken);
    void logout(Long userId);
}
