import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanApplication } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './loan-list.component.html'
})
export class LoanListComponent implements OnInit {
  loans = signal<LoanApplication[]>([]);
  loading = signal(true);
  error = signal('');

  constructor(private loanService: LoanService) {}

  ngOnInit(): void {
    this.loanService.getMyLoans().subscribe({
      next: (loans) => {
        this.loans.set(loans);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load loans.');
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
