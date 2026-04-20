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
public class LoanStatusResponseDTO {

    private long loanId;
    private LoanType loanType;
    private BigDecimal loanAmount;
    private LoanStatus loanStatus;
}