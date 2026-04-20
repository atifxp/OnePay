package com.cts.OnePay.user.repository;

import com.cts.OnePay.user.dto.verificatioDtos.VerificationPendingResponseDto;
import com.cts.OnePay.user.model.Verification;
import com.cts.OnePay.user.model.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification,Long> {

    Optional<Verification> findByUserId(Long userId);
    Page<Verification> findByVerificationStatus(VerificationStatus status, Pageable page);
}
