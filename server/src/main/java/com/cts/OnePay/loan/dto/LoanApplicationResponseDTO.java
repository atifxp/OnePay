package com.cts.OnePay.loan.dto;

import com.cts.OnePay.loan.model.enums.LoanStatus;
import com.cts.OnePay.loan.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponseDTO {

    private long loanId;
    private long userId;
    private LoanType loanType;
    private BigDecimal loanAmount;
    private int tenureMonth;
    private double interestRate;
    private BigDecimal annualIncome;
    private String purpose;
    private LoanStatus loanStatus;
}