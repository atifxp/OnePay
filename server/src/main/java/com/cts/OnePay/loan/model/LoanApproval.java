package com.cts.OnePay.loan.model;

import com.cts.OnePay.loan.model.enums.LoanStatus;
import com.cts.OnePay.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long approvalId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loanId", referencedColumnName = "loanId")
    private LoanApplication loanId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",referencedColumnName = "userId")
    private User officerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

}