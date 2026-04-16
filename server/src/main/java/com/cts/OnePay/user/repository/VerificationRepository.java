package com.cts.OnePay.user.repository;

import com.cts.OnePay.user.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification,Long> {
}
