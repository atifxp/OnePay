package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.dto.userDtos.UserUpdateRequestDto;
import com.cts.OnePay.user.model.MyUserDetails;
import com.cts.OnePay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getProfile(@AuthenticationPrincipal MyUserDetails userDetails){
        Long userId = userDetails.getUserId();
        UserResponseDto response = userService.getProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateRequestDto dto, @AuthenticationPrincipal MyUserDetails userDetails){
        Long userId = userDetails.getUserId();
        UserResponseDto response = userService.updateProfile(userId,dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
