import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanApplication } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-details',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="min-h-screen bg-gray-50 px-4 py-8">
      <div class="max-w-lg mx-auto">

        <div class="mb-6">
          <a routerLink="/loans" class="text-sm text-indigo-600 hover:underline font-medium">← Back to Loans</a>
        </div>

        @if (error()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3">
            {{ error() }}
          </div>
        }

        @if (loading()) {
          <div class="text-center py-12 text-gray-400 text-sm">Loading...</div>
        } @else if (loan()) {
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-6">

            <div class="flex items-center justify-between mb-6">
              <div>
                <h1 class="text-xl font-semibold text-gray-900">{{ loan()!.loanType }} Loan</h1>
                <p class="text-xs text-gray-400 mt-0.5">Loan #{{ loan()!.loanId }}</p>
              </div>
              <span class="text-xs font-medium px-2.5 py-1 rounded-full {{ statusClass(loan()!.loanStatus) }}">
                {{ loan()!.loanStatus }}
              </span>
            </div>

            <div class="space-y-3">
              <div class="flex justify-between py-2 border-b border-gray-100">
                <span class="text-sm text-gray-500">Loan Amount</span>
                <span class="text-sm font-medium text-gray-900">₹{{ formatAmount(loan()!.loanAmount) }}</span>
              </div>
              <div class="flex justify-between py-2 border-b border-gray-100">
                <span class="text-sm text-gray-500">Tenure</span>
                <span class="text-sm font-medium text-gray-900">{{ loan()!.tenureMonth }} months</span>
              </div>
              <div class="flex justify-between py-2 border-b border-gray-100">
                <span class="text-sm text-gray-500">Interest Rate</span>
                <span class="text-sm font-medium text-gray-900">{{ loan()!.interestRate }}% p.a.</span>
              </div>
              <div class="flex justify-between py-2 border-b border-gray-100">
                <span class="text-sm text-gray-500">Annual Income</span>
                <span class="text-sm font-medium text-gray-900">₹{{ formatAmount(loan()!.annualIncome) }}</span>
              </div>
              @if (loan()!.purpose) {
                <div class="flex justify-between py-2 border-b border-gray-100">
                  <span class="text-sm text-gray-500">Purpose</span>
                  <span class="text-sm font-medium text-gray-900">{{ loan()!.purpose }}</span>
                </div>
              }
              <div class="flex justify-between py-2">
                <span class="text-sm text-gray-500">Status</span>
                <span class="text-sm font-medium text-gray-900">{{ loan()!.loanStatus }}</span>
              </div>
            </div>

          </div>
        }

      </div>
    </div>
  `
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
