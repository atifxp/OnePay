package com.cts.OnePay.loan.service;

import com.cts.OnePay.loan.dto.LoanApplicationRequestDTO;
import com.cts.OnePay.loan.dto.LoanApplicationResponseDTO;
import com.cts.OnePay.loan.dto.LoanApprovalRequestDTO;
import com.cts.OnePay.loan.dto.LoanApprovalResponseDTO;
import com.cts.OnePay.loan.dto.LoanStatusResponseDTO;
import com.cts.OnePay.user.model.MyUserDetails;

import java.util.List;

public interface LoanService {

    LoanApplicationResponseDTO applyLoan(LoanApplicationRequestDTO requestDTO, Long userId);

    LoanApplicationResponseDTO getLoanById(Long loanId);

    List<LoanApplicationResponseDTO> getAllLoans();

    List<LoanApplicationResponseDTO> getLoansByUser(Long userId);

    LoanStatusResponseDTO getLoanStatus(Long loanId);

    LoanApprovalResponseDTO updateLoanStatus(LoanApprovalRequestDTO requestDTO, MyUserDetails userDetails);
}