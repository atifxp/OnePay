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
import com.cts.OnePay.transaction.model.Wallet;
import com.cts.OnePay.transaction.repository.WalletRepository;
import com.cts.OnePay.user.model.MyUserDetails;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.model.enums.Role;
import com.cts.OnePay.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private WalletRepository walletRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public LoanApplicationResponseDTO applyLoan(LoanApplicationRequestDTO requestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        LoanApplication loan = modelMapper.map(requestDTO, LoanApplication.class);
        loan.setUser(user);
        loan.setInterestRate(requestDTO.getLoanType().getInterestRate());
        loan.setLoanStatus(LoanStatus.SUBMITTED);

        LoanApplication saved = loanApplicationRepository.save(loan);
        return mapToApplicationResponse(saved);
    }

    @Override
    public LoanApplicationResponseDTO getLoanById(Long loanId, MyUserDetails userDetails) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));
        if (userDetails.getRole() == Role.CUSTOMER
                && !loan.getUser().getUserId().equals(userDetails.getUserId())) {
            throw new RuntimeException("Access denied: you can only view your own loans.");
        }
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
    public LoanStatusResponseDTO getLoanStatus(Long loanId, MyUserDetails userDetails) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));
        if (userDetails.getRole() == Role.CUSTOMER
                && !loan.getUser().getUserId().equals(userDetails.getUserId())) {
            throw new RuntimeException("Access denied: you can only view your own loans.");
        }
        return modelMapper.map(loan, LoanStatusResponseDTO.class);
    }

    @Override
    @Transactional
    public LoanApprovalResponseDTO updateLoanStatus(LoanApprovalRequestDTO requestDTO, MyUserDetails userDetails) {
        LoanApplication loan = loanApplicationRepository.findById(requestDTO.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + requestDTO.getLoanId()));

        User officer = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("Officer not found with ID: " + userDetails.getUserId()));

        if (loan.getLoanStatus() == LoanStatus.APPROVED || loan.getLoanStatus() == LoanStatus.REJECTED) {
            throw new RuntimeException("Loan is already " + loan.getLoanStatus() + " and cannot be updated.");
        }

        loan.setLoanStatus(requestDTO.getLoanStatus());
        loanApplicationRepository.save(loan);

        if (requestDTO.getLoanStatus() == LoanStatus.APPROVED) {
            Wallet wallet = walletRepository.findByUser_UserId(loan.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + loan.getUser().getUserId()));
            wallet.setBalance(wallet.getBalance().add(loan.getLoanAmount()));
            walletRepository.save(wallet);
        }

        Optional<LoanApproval> existingApproval = loanApprovalRepository.findByLoanId_LoanId(requestDTO.getLoanId());

        LoanApproval approval;
        if (existingApproval.isPresent()) {
            loanApprovalRepository.updateApproval(
                    requestDTO.getLoanId(),
                    officer,
                    requestDTO.getLoanStatus()
            );
            approval = loanApprovalRepository.findByLoanId_LoanId(requestDTO.getLoanId()).get();
        } else {
            approval = new LoanApproval();
            approval.setLoanId(loan);
            approval.setOfficerId(officer);
            approval.setLoanStatus(requestDTO.getLoanStatus());
            approval = loanApprovalRepository.save(approval);
        }

        return mapToApprovalResponse(approval);
    }

    private LoanApplicationResponseDTO mapToApplicationResponse(LoanApplication loan) {
        LoanApplicationResponseDTO responseDTO = modelMapper.map(loan, LoanApplicationResponseDTO.class);
        responseDTO.setUserId(loan.getUser().getUserId());
        return responseDTO;
    }

    private LoanApprovalResponseDTO mapToApprovalResponse(LoanApproval approval) {
        LoanApprovalResponseDTO responseDTO = new LoanApprovalResponseDTO();
        responseDTO.setApprovalId(approval.getApprovalId());
        responseDTO.setLoanId(approval.getLoanId().getLoanId());
        responseDTO.setOfficerId(approval.getOfficerId().getUserId());
        responseDTO.setLoanStatus(approval.getLoanStatus());
        return responseDTO;
    }
}