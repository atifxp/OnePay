package com.cts.OnePay.loan.repository;

import com.cts.OnePay.loan.model.LoanApproval;
import com.cts.OnePay.loan.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApprovalRepository extends JpaRepository<LoanApproval, Long> {

    // Get approval record by loan ID
    Optional<LoanApproval> findByLoanId_LoanId(Long loanId);

    // Get all approvals handled by a specific officer
    List<LoanApproval> findByOfficer_Id_UserId(Long officerId);

    // Get all approvals by status (APPROVED / REJECTED)
    List<LoanApproval> findByLoanStatus(LoanStatus loanStatus);
}