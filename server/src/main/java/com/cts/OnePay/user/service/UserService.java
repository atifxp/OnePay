package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.UserResponseDto;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
     UserResponseDto getProfile(Long userId);


     UserResponseDto updateProfile();


}
