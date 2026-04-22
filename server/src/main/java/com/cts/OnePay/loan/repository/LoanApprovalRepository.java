package com.cts.OnePay.loan.repository;

import com.cts.OnePay.loan.model.LoanApproval;
import com.cts.OnePay.loan.model.enums.LoanStatus;
import com.cts.OnePay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApprovalRepository extends JpaRepository<LoanApproval, Long> {


    Optional<LoanApproval> findByLoanId_LoanId(Long loanId);

    List<LoanApproval> findByOfficerId_UserId(Long officerId);

    List<LoanApproval> findByLoanStatus(LoanStatus loanStatus);

    @Modifying
    @Query("UPDATE LoanApproval la SET la.loanStatus = :status, la.officerId = :officer WHERE la.loanId.loanId = :loanId")
    int updateApproval(@Param("loanId") Long loanId, @Param("officer") User officer, @Param("status") LoanStatus status);
}