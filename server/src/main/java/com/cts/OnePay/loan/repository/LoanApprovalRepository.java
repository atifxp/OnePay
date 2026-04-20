package com.cts.OnePay.loan.repository;

import com.cts.OnePay.loan.model.LoanApproval;
import com.cts.OnePay.loan.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApprovalRepository extends JpaRepository<LoanApproval, Long> {


    Optional<LoanApproval> findByLoanId(Long loanId);

    List<LoanApproval> findByOfficerId(Long officerId);

    List<LoanApproval> findByLoanStatus(LoanStatus loanStatus);
}