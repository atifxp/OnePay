package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(){
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateProfile(){
        return ResponseEntity.ok(null);
    }
}
