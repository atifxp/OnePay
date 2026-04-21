package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserLoginRequestDto;
import com.cts.OnePay.user.model.User;
import org.springframework.http.ResponseCookie;

import java.util.Map;

public interface AuthService {
    Map<String, String> register(User user);
    Map<String, Object> login(UserLoginRequestDto user, String ipAddress);
    Map<String, Object> refresh(String phoneNo);
    void logout(Long userId);


}
