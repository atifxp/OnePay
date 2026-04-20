package com.cts.OnePay.loan.service;

import com.cts.OnePay.loan.dto.LoanApplicationRequestDTO;
import com.cts.OnePay.loan.dto.LoanApplicationResponseDTO;
import com.cts.OnePay.loan.dto.LoanApprovalRequestDTO;
import com.cts.OnePay.loan.dto.LoanApprovalResponseDTO;
import com.cts.OnePay.loan.dto.LoanStatusResponseDTO;
import com.cts.OnePay.loan.model.LoanApplication;
import com.cts.OnePay.loan.model.LoanApproval;
import com.cts.OnePay.loan.model.enums.LoanStatus;
import com.cts.OnePay.loan.repository.LoanApplicationRepository;
import com.cts.OnePay.loan.repository.LoanApprovalRepository;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanApprovalRepository loanApprovalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LoanApplicationResponseDTO applyLoan(LoanApplicationRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDTO.getUserId()));

        LoanApplication loan = modelMapper.map(requestDTO, LoanApplication.class);
        loan.setUser(user);
        loan.setInterestRate(requestDTO.getLoanType().getInterestRate());
        loan.setLoanStatus(LoanStatus.SUBMITTED);

        LoanApplication saved = loanApplicationRepository.save(loan);
        return mapToApplicationResponse(saved);
    }

    @Override
    public LoanApplicationResponseDTO getLoanById(Long loanId) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));
        return mapToApplicationResponse(loan);
    }

    @Override
    public List<LoanApplicationResponseDTO> getAllLoans() {
        return loanApplicationRepository.findAll()
                .stream()
                .map(this::mapToApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanApplicationResponseDTO> getLoansByUser(Long userId) {
        return loanApplicationRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LoanStatusResponseDTO getLoanStatus(Long loanId) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));
        return modelMapper.map(loan, LoanStatusResponseDTO.class);
    }

    @Override
    public LoanApprovalResponseDTO updateLoanStatus(LoanApprovalRequestDTO requestDTO) {
        LoanApplication loan = loanApplicationRepository.findById(requestDTO.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + requestDTO.getLoanId()));

        User officer = userRepository.findById(requestDTO.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found with ID: " + requestDTO.getOfficerId()));

        if (loan.getLoanStatus() == LoanStatus.APPROVED || loan.getLoanStatus() == LoanStatus.REJECTED) {
            throw new RuntimeException("Loan is already " + loan.getLoanStatus() + " and cannot be updated.");
        }

        loan.setLoanStatus(requestDTO.getLoanStatus());
        loanApplicationRepository.save(loan);

        LoanApproval approval = new LoanApproval();
        approval.setLoanId(loan);
        approval.setOfficerId(officer);
        approval.setLoanStatus(requestDTO.getLoanStatus());

        LoanApproval saved = loanApprovalRepository.save(approval);
        return mapToApprovalResponse(saved);
    }

    private LoanApplicationResponseDTO mapToApplicationResponse(LoanApplication loan) {
        LoanApplicationResponseDTO responseDTO = modelMapper.map(loan, LoanApplicationResponseDTO.class);
        responseDTO.setUserId(loan.getUser().getUserId());
        return responseDTO;
    }

    private LoanApprovalResponseDTO mapToApprovalResponse(LoanApproval approval) {
        LoanApprovalResponseDTO responseDTO = modelMapper.map(approval, LoanApprovalResponseDTO.class);
        responseDTO.setLoanId(approval.getLoanId().getLoanId());
        responseDTO.setOfficerId(approval.getOfficerId().getUserId());
        return responseDTO;
    }
}