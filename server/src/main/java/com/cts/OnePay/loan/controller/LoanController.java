package com.cts.OnePay.loan.controller;

import com.cts.OnePay.loan.dto.LoanApplicationRequestDTO;
import com.cts.OnePay.loan.dto.LoanApplicationResponseDTO;
import com.cts.OnePay.loan.dto.LoanApprovalRequestDTO;
import com.cts.OnePay.loan.dto.LoanApprovalResponseDTO;
import com.cts.OnePay.loan.dto.LoanStatusResponseDTO;
import com.cts.OnePay.loan.service.LoanService;
import com.cts.OnePay.user.model.MyUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponseDTO> applyLoan(
            @Valid @RequestBody LoanApplicationRequestDTO requestDTO,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        LoanApplicationResponseDTO response = loanService.applyLoan(requestDTO, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplicationResponseDTO> getLoanById(
            @PathVariable Long loanId,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        LoanApplicationResponseDTO response = loanService.getLoanById(loanId, userDetails);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LOAN_OFFICER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<LoanApplicationResponseDTO>> getAllLoans() {
        List<LoanApplicationResponseDTO> response = loanService.getAllLoans();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-loans")
    public ResponseEntity<List<LoanApplicationResponseDTO>> getMyLoans(
            @AuthenticationPrincipal MyUserDetails userDetails) {
        List<LoanApplicationResponseDTO> response = loanService.getLoansByUser(userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{loanId}/status")
    public ResponseEntity<LoanStatusResponseDTO> getLoanStatus(
            @PathVariable Long loanId,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        LoanStatusResponseDTO response = loanService.getLoanStatus(loanId, userDetails);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('LOAN_OFFICER') or hasRole('ADMIN')")
    @PostMapping("/approval")
    public ResponseEntity<LoanApprovalResponseDTO> updateLoanStatus(
            @Valid @RequestBody LoanApprovalRequestDTO requestDTO, @AuthenticationPrincipal MyUserDetails userDetails) {
        LoanApprovalResponseDTO response = loanService.updateLoanStatus(requestDTO,userDetails);
        return ResponseEntity.ok(response);
    }
}