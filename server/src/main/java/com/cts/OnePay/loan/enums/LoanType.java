package com.cts.OnePay.loan.enums;

public enum LoanType {
    PERSONAL(7.5),
    HOME(8),
    VEHICLE(9.5),
    EDUCATION(4.5),
    OTHER(10);

    private double interestRate;

    LoanType(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }
}