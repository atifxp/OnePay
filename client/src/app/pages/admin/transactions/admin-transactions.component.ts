import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { TransactionService, Transaction } from '../../../services/transaction.service';

@Component({
  selector: 'app-admin-transactions',
  standalone: true,
  imports: [],
  template: `
    <div class="min-h-screen bg-gray-50 px-4 py-8">
      <div class="max-w-4xl mx-auto">

        <div class="mb-6">
          <h1 class="text-2xl font-semibold text-gray-900">All Transactions</h1>
          <p class="text-sm text-gray-500 mt-1">Platform-wide transaction history</p>
        </div>

        @if (error()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ error() }}
          </div>
        }

        @if (loading()) {
          <div class="text-center py-12 text-gray-400 text-sm">Loading...</div>
        } @else if (transactions().length === 0) {
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-8 text-center">
            <p class="text-gray-500 text-sm">No transactions found.</p>
          </div>
        } @else {
          <div class="space-y-3">
            @for (tx of transactions(); track tx.transactionId) {
              <div class="bg-white border border-gray-200 rounded-xl shadow-sm px-5 py-4">
                <div class="flex items-start justify-between gap-4">

                  <div class="flex-1 min-w-0">
                    <div class="flex items-center gap-2 mb-1">
                      <p class="text-sm font-medium text-gray-900">
                        {{ tx.senderWallet.userFullName }} → {{ tx.receiverWallet.userFullName }}
                      </p>
                      <span class="text-xs font-medium px-2 py-0.5 rounded-full {{ statusClass(tx.transactionStatus) }}">
                        {{ tx.transactionStatus }}
                      </span>
                    </div>
                    <p class="text-xs text-gray-500">
                      ₹{{ formatAmount(tx.amount) }}
                      @if (tx.message) { · {{ tx.message }} }
                    </p>
                    <p class="text-xs text-gray-400 mt-0.5">
                      #{{ tx.transactionId }} · {{ formatDate(tx.initiatedAt) }}
                    </p>
                  </div>

                </div>
              </div>
            }
          </div>

          <div class="flex items-center justify-between mt-6">
            <p class="text-xs text-gray-500">
              Page {{ page() + 1 }} of {{ totalPages() }} · {{ totalElements() }} total
            </p>
            <div class="flex gap-2">
              <button
                (click)="prevPage()"
                [disabled]="page() === 0"
                class="border border-gray-200 rounded-lg px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-50 disabled:opacity-40 transition-colors"
              >
                Previous
              </button>
              <button
                (click)="nextPage()"
                [disabled]="page() + 1 >= totalPages()"
                class="border border-gray-200 rounded-lg px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-50 disabled:opacity-40 transition-colors"
              >
                Next
              </button>
            </div>
          </div>
        }

      </div>
    </div>
  `
})
export class AdminTransactionsComponent implements OnInit {
  transactions = signal<Transaction[]>([]);
  loading = signal(true);
  error = signal('');
  page = signal(0);
  totalPages = signal(0);
  totalElements = signal(0);
  private readonly pageSize = 15;

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadPage();
  }

  private loadPage(): void {
    this.loading.set(true);
    this.error.set('');
    this.transactionService.getAll(this.page(), this.pageSize).subscribe({
      next: (res) => {
        this.transactions.set(res.content);
        this.totalPages.set(res.totalPages);
        this.totalElements.set(res.totalElements);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load transactions.');
        this.loading.set(false);
      }
    });
  }

  prevPage(): void {
    this.page.update(p => p - 1);
    this.loadPage();
  }

  nextPage(): void {
    this.page.update(p => p + 1);
    this.loadPage();
  }

  formatAmount(amount: number): string {
    return amount.toLocaleString('en-IN');
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleString('en-IN', {
      day: '2-digit', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      COMPLETED: 'bg-green-50 text-green-700',
      FAILED: 'bg-red-50 text-red-700',
      PENDING: 'bg-yellow-50 text-yellow-700',
    };
    return map[status] ?? 'bg-gray-100 text-gray-600';
  }
}
