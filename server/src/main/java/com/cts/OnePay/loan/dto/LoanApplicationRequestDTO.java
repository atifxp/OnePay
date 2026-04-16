package com.cts.OnePay.loan.dto;

import com.cts.OnePay.loan.model.enums.LoanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "1000.00", message = "Loan amount must be at least 1000")
    private BigDecimal loanAmount;

    @Min(value = 1, message = "Tenure must be at least 1 month")
    private int tenureMonth;

    @NotNull(message = "Annual income is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Annual income must be greater than 0")
    private BigDecimal annualIncome;

    private String purpose;
}