package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.verificatioDtos.VerificationPendingResponseDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationRequestDto;
import com.cts.OnePay.user.dto.verificatioDtos.VerificationUpdateRequestDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.model.Verification;
import com.cts.OnePay.user.model.enums.VerificationStatus;
import com.cts.OnePay.user.repository.UserRepository;
import com.cts.OnePay.user.repository.VerificationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class VerificationServiceImpl implements VerificationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Map<String, String> submitDoc(Long userId,VerificationRequestDto verificationRequest) {
        //check for user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist for verification"));


        //check for existing verification request
        Optional<Verification> exists = verificationRepository.findByUser_UserId(userId);

        if(exists.isPresent()){
            VerificationStatus status = exists.get().getVerificationStatus();

            if (status == VerificationStatus.PENDING) {
                throw new IllegalStateException("Verification already under review");
            }

            if (status == VerificationStatus.VERIFIED) {
                throw new IllegalStateException("User is already verified");
            }

        }
        //create new verification entry if not exists
        Verification newRequest = new Verification();
        newRequest.setUser(user);
        newRequest.setDocNumber(verificationRequest.getDocNumber());
        newRequest.setDocType(verificationRequest.getDocType());

        Verification response = verificationRepository.save(newRequest);

        return Map.of(
                "message", "Documents for verification submitted successfully",
                "status", response.getVerificationStatus().name()
        );

    }

    @Override
    public Map<String, String> checkStatus(Long userId) {
        Verification exists = verificationRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Verification Documents not submitted"));
        return Map.of("status", exists.getVerificationStatus().name());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Map<String, String> approve(Long verifierId,Long userId, VerificationUpdateRequestDto updates) {

        //check for user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        //get verifier reference
        User verifierProxy = userRepository.getReferenceById(verifierId);

        //fetch verification object
        Verification fetchedVerification = verificationRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Verification Request for the user does not exist"));

        //update verification status
        fetchedVerification.setVerificationStatus(updates.getVerificationStatus());
        fetchedVerification.setVerifier(verifierProxy);

        return Map.of("message", "User "+ user.getFullName() +" verified successfully");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Map<String, String> reject(Long verifierId,Long userId, VerificationUpdateRequestDto updates) {
        //check for user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        //get verifier reference
        User verifierProxy = userRepository.getReferenceById(verifierId);

        //fetch verification object
        Verification fetchedVerification = verificationRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Verification Request for the user does not exist"));

        //update verification status
        fetchedVerification.setVerificationStatus(updates.getVerificationStatus());
        fetchedVerification.setVerifier(verifierProxy);
        return Map.of("message", "User "+ user.getFullName() +" verification rejected");
    }

    @Override
    public Page<VerificationPendingResponseDto> fetchPending(int pageNo, int pageSize) {

        Page<Verification> verifications = verificationRepository.findByVerificationStatus(
                VerificationStatus.PENDING,
                PageRequest.of(pageNo - 1,pageSize)
        );
        System.out.println(verifications.getTotalElements());
        return verifications.map(
                item -> modelMapper.map(item,VerificationPendingResponseDto.class)
        );
    }
}
