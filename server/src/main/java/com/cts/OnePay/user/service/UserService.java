package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.dto.userDtos.UserUpdateRequestDto;

public interface UserService {
     UserResponseDto getProfile(Long userId);


     UserResponseDto updateProfile(Long userId, UserUpdateRequestDto dto);


}
