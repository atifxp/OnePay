package com.cts.OnePay.loan.dto;

import com.cts.OnePay.loan.model.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApprovalResponseDTO {

    private long approvalId;
    private long loanId;
    private long officerId;
    private LoanStatus loanStatus;
}