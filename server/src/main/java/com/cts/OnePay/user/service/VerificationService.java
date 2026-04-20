package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.verificatioDtos.VerificationPendingResponseDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationRequestDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationUpdateRequestDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface VerificationService {
    Map<String, String> submitDoc(Long userId, VerificationRequestDto verificationRequest);

    Map<String,String> checkStatus(Long userId);

    Map<String,String> approve(Long verifierId,Long userId, VerificationUpdateRequestDto updates);

    Map<String, String> reject(Long verifierId,Long userId, VerificationUpdateRequestDto updates);

    Page<VerificationPendingResponseDto> fetchPending(int pageNo, int pageSize);
}
