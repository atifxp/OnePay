package com.cts.OnePay.loan.repository;

import com.cts.OnePay.loan.model.LoanApplication;
import com.cts.OnePay.loan.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    // Get all loans for a specific user
    List<LoanApplication> findByUser_UserId(Long userId);

    // Get all loans by status
    List<LoanApplication> findByLoanStatus(LoanStatus loanStatus);

    // Get all loans for a user filtered by status
    List<LoanApplication> findByUser_UserIdAndLoanStatus(Long userId, LoanStatus loanStatus);
}