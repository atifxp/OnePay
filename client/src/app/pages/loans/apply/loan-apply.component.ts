import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanType } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-apply',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './loan-apply.component.html'
})
export class LoanApplyComponent {
  loanType: LoanType = 'PERSONAL';
  loanAmount: number | null = null;
  tenureMonth: number | null = null;
  annualIncome: number | null = null;
  purpose = '';
  loading = signal(false);
  error = signal('');
  success = signal('');

  constructor(private loanService: LoanService, private router: Router) {}

  onSubmit(): void {
    this.error.set('');
    this.success.set('');
    this.loading.set(true);

    this.loanService.apply({
      loanType: this.loanType,
      loanAmount: this.loanAmount!,
      tenureMonth: this.tenureMonth!,
      annualIncome: this.annualIncome!,
      purpose: this.purpose || undefined,
    }).subscribe({
      next: (res) => {
        this.success.set('Loan application submitted successfully!');
        this.loading.set(false);
        setTimeout(() => this.router.navigate(['/loans', res.loanId]), 1500);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to submit application. Please try again.');
        this.loading.set(false);
      }
    });
  }
}
