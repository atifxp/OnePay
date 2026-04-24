package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserPromoteRequestDto;
import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.dto.userDtos.UserUpdateRequestDto;
import com.cts.OnePay.user.model.User;

import java.util.Map;

public interface UserService {

     UserResponseDto getById(Long userId);

     UserResponseDto getProfile(Long userId);


     UserResponseDto updateProfile(Long userId, UserUpdateRequestDto dto);

     Map<String,String> promoteUser(UserPromoteRequestDto dto);

}
