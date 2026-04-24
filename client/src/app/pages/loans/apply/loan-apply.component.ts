import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { LoanService, LoanType } from '../../../services/loan.service';

@Component({
  selector: 'app-loan-apply',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="min-h-screen bg-gray-50 flex items-center justify-center px-4 py-8">
      <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-8 w-full max-w-md">

        <h1 class="text-2xl font-semibold text-gray-900">Apply for a Loan</h1>
        <p class="text-sm text-gray-500 mt-1 mb-6">Fill in the details to submit your loan application</p>

        @if (error()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ error() }}
          </div>
        }

        @if (success()) {
          <div class="bg-green-50 border border-green-200 text-green-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ success() }}
          </div>
        }

        <form (ngSubmit)="onSubmit()" #applyForm="ngForm" class="space-y-4">

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Loan Type</label>
            <select
              name="loanType"
              [(ngModel)]="loanType"
              required
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent bg-white"
            >
              <option value="PERSONAL">Personal (7.5% p.a.)</option>
              <option value="HOME">Home (8% p.a.)</option>
              <option value="VEHICLE">Vehicle (9.5% p.a.)</option>
              <option value="EDUCATION">Education (4.5% p.a.)</option>
              <option value="OTHER">Other (10% p.a.)</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Loan Amount (₹)</label>
            <input
              type="number"
              name="loanAmount"
              [(ngModel)]="loanAmount"
              required
              min="1000"
              placeholder="Minimum ₹1,000"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Tenure (months)</label>
            <input
              type="number"
              name="tenureMonth"
              [(ngModel)]="tenureMonth"
              required
              min="1"
              placeholder="e.g. 12"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Annual Income (₹)</label>
            <input
              type="number"
              name="annualIncome"
              [(ngModel)]="annualIncome"
              required
              min="1"
              placeholder="Your annual income"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Purpose <span class="text-gray-400">(optional)</span></label>
            <input
              type="text"
              name="purpose"
              [(ngModel)]="purpose"
              placeholder="Brief description of loan purpose"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <button
            type="submit"
            [disabled]="loading() || applyForm.invalid"
            class="w-full bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50 text-white rounded-lg py-2 text-sm font-medium transition-colors"
          >
            {{ loading() ? 'Submitting...' : 'Submit Application' }}
          </button>
        </form>

        <p class="text-sm text-gray-500 text-center mt-6">
          <a routerLink="/loans" class="text-indigo-600 hover:underline font-medium">Back to My Loans</a>
        </p>

      </div>
    </div>
  `
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
