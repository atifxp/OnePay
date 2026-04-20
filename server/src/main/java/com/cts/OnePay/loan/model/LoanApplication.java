package com.cts.OnePay.loan.model;

import com.cts.OnePay.loan.model.enums.LoanStatus;
import com.cts.OnePay.loan.model.enums.LoanType;
import com.cts.OnePay.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanType loanType = LoanType.PERSONAL;

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private int tenureMonth;

    @NotNull
    private double interestRate = loanType.getInterestRate();

    @NotNull
    private BigDecimal annualIncome;

    private String purpose;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus = LoanStatus.SUBMITTED;
}
