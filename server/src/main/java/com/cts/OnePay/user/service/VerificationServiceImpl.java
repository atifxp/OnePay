package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.VerificationUpdateRequestDto;
import com.cts.OnePay.user.model.Verification;
import com.cts.OnePay.user.repository.UserRepository;
import com.cts.OnePay.user.repository.VerificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VerificationServiceImpl implements VerificationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public Map<String, Object> submitDoc(Verification verification) {
        return Map.of();
    }

    @Override
    public Map<String, Object> checkStatus(Long userId) {
        return Map.of();
    }

    @Override
    @Transactional
    public Map<String, Object> approve(Long userId, VerificationUpdateRequestDto updates) {
        return Map.of();
    }

    @Override
    @Transactional
    public Map<String, Object> reject(Long userId, VerificationUpdateRequestDto updates) {
        return Map.of();
    }

}
