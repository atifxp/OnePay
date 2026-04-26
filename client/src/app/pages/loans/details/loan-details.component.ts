import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanApplication } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-details',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './loan-details.component.html'
})
export class LoanDetailsComponent implements OnInit {
  loan = signal<LoanApplication | null>(null);
  loading = signal(true);
  error = signal('');

  constructor(private route: ActivatedRoute, private loanService: LoanService) {}

  ngOnInit(): void {
    const loanId = Number(this.route.snapshot.paramMap.get('loanId'));
    this.loanService.getById(loanId).subscribe({
      next: (loan) => {
        this.loan.set(loan);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load loan details.');
        this.loading.set(false);
      }
    });
  }

  formatAmount(amount: number): string {
    return amount.toLocaleString('en-IN');
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      APPROVED: 'bg-green-50 text-green-700',
      REJECTED: 'bg-red-50 text-red-700',
      UNDER_REVIEW: 'bg-yellow-50 text-yellow-700',
      SUBMITTED: 'bg-blue-50 text-blue-700',
    };
    return map[status] ?? 'bg-gray-100 text-gray-600';
  }
}
