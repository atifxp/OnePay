import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanApplication } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="min-h-screen bg-gray-50 px-4 py-8">
      <div class="max-w-2xl mx-auto">

        <div class="flex items-center justify-between mb-6">
          <div>
            <h1 class="text-2xl font-semibold text-gray-900">My Loans</h1>
            <p class="text-sm text-gray-500 mt-1">Track your loan applications</p>
          </div>
          <a routerLink="/loans/apply"
            class="bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg px-4 py-2 text-sm font-medium transition-colors">
            Apply for Loan
          </a>
        </div>

        @if (error()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ error() }}
          </div>
        }

        @if (loading()) {
          <div class="text-center py-12 text-gray-400 text-sm">Loading...</div>
        } @else if (loans().length === 0) {
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-8 text-center">
            <p class="text-gray-500 text-sm">No loans found.</p>
            <a routerLink="/loans/apply" class="text-indigo-600 hover:underline text-sm font-medium mt-2 inline-block">
              Apply for your first loan
            </a>
          </div>
        } @else {
          <div class="space-y-3">
            @for (loan of loans(); track loan.loanId) {
              <a [routerLink]="['/loans', loan.loanId]"
                class="block bg-white border border-gray-200 rounded-xl shadow-sm px-5 py-4 hover:border-indigo-300 transition-colors">
                <div class="flex items-center justify-between">
                  <div>
                    <p class="text-sm font-medium text-gray-900">{{ loan.loanType }} Loan</p>
                    <p class="text-xs text-gray-500 mt-0.5">
                      ₹{{ formatAmount(loan.loanAmount) }} · {{ loan.tenureMonth }} months
                    </p>
                  </div>
                  <span class="text-xs font-medium px-2.5 py-1 rounded-full {{ statusClass(loan.loanStatus) }}">
                    {{ loan.loanStatus }}
                  </span>
                </div>
                <p class="text-xs text-gray-400 mt-2">{{ loan.interestRate }}% p.a. · Loan #{{ loan.loanId }}</p>
              </a>
            }
          </div>
        }

      </div>
    </div>
  `
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
