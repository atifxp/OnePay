package com.cts.OnePay.loan.dto;

import com.cts.OnePay.loan.model.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApprovalRequestDTO {

    @NotNull(message = "Loan ID is required")
    private Long loanId;

    @NotNull(message = "Loan status is required")
    private LoanStatus loanStatus;
}