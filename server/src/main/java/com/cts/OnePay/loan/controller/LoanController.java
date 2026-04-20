package com.cts.OnePay.loan.controller;

import com.cts.OnePay.loan.dto.LoanApplicationRequestDTO;
import com.cts.OnePay.loan.dto.LoanApplicationResponseDTO;
import com.cts.OnePay.loan.dto.LoanApprovalRequestDTO;
import com.cts.OnePay.loan.dto.LoanApprovalResponseDTO;
import com.cts.OnePay.loan.dto.LoanStatusResponseDTO;
import com.cts.OnePay.loan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResponseDTO> applyLoan(
            @Valid @RequestBody LoanApplicationRequestDTO requestDTO) {
        LoanApplicationResponseDTO response = loanService.applyLoan(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanApplicationResponseDTO> getLoanById(
            @PathVariable Long loanId) {
        LoanApplicationResponseDTO response = loanService.getLoanById(loanId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponseDTO>> getAllLoans() {
        List<LoanApplicationResponseDTO> response = loanService.getAllLoans();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanApplicationResponseDTO>> getLoansByUser(
            @PathVariable Long userId) {
        List<LoanApplicationResponseDTO> response = loanService.getLoansByUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{loanId}/status")
    public ResponseEntity<LoanStatusResponseDTO> getLoanStatus(
            @PathVariable Long loanId) {
        LoanStatusResponseDTO response = loanService.getLoanStatus(loanId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approval")
    public ResponseEntity<LoanApprovalResponseDTO> updateLoanStatus(
            @Valid @RequestBody LoanApprovalRequestDTO requestDTO) {
        LoanApprovalResponseDTO response = loanService.updateLoanStatus(requestDTO);
        return ResponseEntity.ok(response);
    }
}