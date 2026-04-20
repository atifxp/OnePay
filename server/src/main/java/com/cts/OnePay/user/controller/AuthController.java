package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserResponseDto dto){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest req, HttpServletResponse res){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res){
        return ResponseEntity.ok(null);
    }

}
