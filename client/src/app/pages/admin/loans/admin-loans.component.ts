import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanApplication, LoanStatus } from '../../../services/loan.service';

@Component({
  selector: 'app-admin-loans',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './admin-loans.component.html'
})
export class AdminLoansComponent implements OnInit {
  loans = signal<LoanApplication[]>([]);
  loading = signal(true);
  updating = signal<number | null>(null);
  error = signal('');
  successMsg = signal('');
  selectedStatus: Record<number, LoanStatus> = {};

  constructor(private loanService: LoanService) {}

  ngOnInit(): void {
    this.loanService.getAll().subscribe({
      next: (loans) => {
        this.loans.set(loans);
        loans.forEach(l => this.selectedStatus[l.loanId] = l.loanStatus);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load loans.');
        this.loading.set(false);
      }
    });
  }

  updateStatus(loanId: number): void {
    this.error.set('');
    this.successMsg.set('');
    this.updating.set(loanId);

    this.loanService.updateStatus({ loanId, loanStatus: this.selectedStatus[loanId] }).subscribe({
      next: (res) => {
        this.loans.update(list =>
          list.map(l => l.loanId === loanId ? { ...l, loanStatus: res.loanStatus } : l)
        );
        this.successMsg.set(`Loan #${loanId} updated to ${res.loanStatus}.`);
        this.updating.set(null);
        setTimeout(() => this.successMsg.set(''), 3000);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to update status.');
        this.updating.set(null);
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
