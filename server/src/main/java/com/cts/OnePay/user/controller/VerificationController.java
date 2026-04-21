package com.cts.OnePay.user.controller;

import com.cts.OnePay.user.dto.verificatioDtos.VerificationPendingResponseDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationRequestDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationUpdateRequestDto;
import com.cts.OnePay.user.model.MyUserDetails;
import com.cts.OnePay.user.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String,String>> submit(@RequestBody @Valid VerificationRequestDto verificationRequest, @AuthenticationPrincipal MyUserDetails userDetails){
        Long userId = userDetails.getUserId();
        Map<String, String> response = verificationService.submitDoc(userId,verificationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String,String>> checkStatus(@AuthenticationPrincipal MyUserDetails userDetails){
        Long userId = userDetails.getUserId();
        Map<String, String> response = verificationService.checkStatus(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //ADMIN routes

    @PatchMapping("/{userId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> approve(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates, @AuthenticationPrincipal MyUserDetails userDetails){
        Long verifierId = userDetails.getUserId();
        Map<String, String> response = verificationService.approve(verifierId, userId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{userId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> reject(@PathVariable("userId")Long userId, @RequestBody VerificationUpdateRequestDto updates, @AuthenticationPrincipal MyUserDetails userDetails){
        Long verifierId = userDetails.getUserId();
        Map<String, String> response = verificationService.reject(verifierId, userId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pending/{pageNo}/{pageSize}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<VerificationPendingResponseDto>> fetchPendingVerificationRequest(
            @PathVariable("pageNo")int pageNo,
            @PathVariable("pageSize") int pageSize
    ){
        Page<VerificationPendingResponseDto> response = verificationService.fetchPending(pageNo, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
