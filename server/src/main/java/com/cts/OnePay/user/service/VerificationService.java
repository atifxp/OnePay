package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.VerificationUpdateRequestDto;
import com.cts.OnePay.user.model.Verification;

import java.util.Map;

public interface VerificationService {
    Map<String, Object> submitDoc(Verification verification);

    Map<String,Object> checkStatus(Long userId);

    Map<String,Object> approve(Long userId, VerificationUpdateRequestDto updates);

    Map<String, Object> reject(Long userId, VerificationUpdateRequestDto updates);
    
}
