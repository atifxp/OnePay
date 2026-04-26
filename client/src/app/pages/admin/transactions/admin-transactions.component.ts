import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { TransactionService, Transaction } from '../../../services/transaction.service';

@Component({
  selector: 'app-admin-transactions',
  standalone: true,
  imports: [],
  templateUrl: './admin-transactions.component.html'
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
