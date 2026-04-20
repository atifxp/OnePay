package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.verificatioDtos.VerificationPendingResponseDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationRequestDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationUpdateRequestDto;
import com.cts.OnePay.user.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String,String>> submit(@RequestBody @Valid VerificationRequestDto verificationRequest){
        Long userId = -1L; //Placeholder: To be Changed
        Map<String, String> response = verificationService.submitDoc(userId,verificationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String,String>> checkStatus(){
        Long userId = -1L;
        Map<String, String> response = verificationService.checkStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //ADMIN routes

    @PatchMapping("/{userId}/approve")
    public ResponseEntity<Map<String, String>> approve(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates){
        Long verifierId = -1L;
        Map<String, String> response = verificationService.approve(verifierId, userId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{userId}/reject")
    public ResponseEntity<Map<String, String>> reject(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates){
        Long verifierId = -1L;
        Map<String, String> response = verificationService.reject(verifierId, userId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pending/{pageNo}/{pageSize}")
    public ResponseEntity<Page<VerificationPendingResponseDto>> fetchPendingVerificationRequest(
            @PathVariable("pageNo")int pageNo,
            @PathVariable("pageSize") int pageSize
    ){
        Page<VerificationPendingResponseDto> response = verificationService.fetchPending(pageNo, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
