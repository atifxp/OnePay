package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.VerificationUpdateRequestDto;
import com.cts.OnePay.user.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/submit")
    public ResponseEntity<?> submit(){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/status")
    public ResponseEntity<?> checkStatus(){
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{userId}/approve")
    public ResponseEntity<?> approve(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates){
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{userId}/reject")
    public ResponseEntity<?> reject(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates){
        return ResponseEntity.ok(null);
    }
}
