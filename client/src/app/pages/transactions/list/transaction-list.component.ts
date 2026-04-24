import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { TransactionService, Transaction } from '../../../services/transaction.service';

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './transaction-list.component.html'
})
export class TransactionListComponent implements OnInit {
  transactions = signal<Transaction[]>([]);
  totalPages = signal(0);
  loading = signal(true);
  error = signal('');

  page = 0;
  pageSize = 10;

  // Transfer form
  showTransferForm = signal(false);
  receiverUserId = 0;
  amount = 0;
  transferMessage = '';
  transferring = signal(false);
  transferError = signal('');
  transferSuccess = signal('');

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.loading.set(true);
    this.error.set('');
    this.transactionService.getMyTransactions(this.page, this.pageSize).subscribe({
      next: (res) => {
        this.transactions.set(res.content);
        this.totalPages.set(res.totalPages);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load transactions.');
        this.loading.set(false);
      }
    });
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadTransactions();
    }
  }

  nextPage(): void {
    if (this.page < this.totalPages() - 1) {
      this.page++;
      this.loadTransactions();
    }
  }

  onTransfer(): void {
    this.transferError.set('');
    this.transferSuccess.set('');
    this.transferring.set(true);

    this.transactionService.transfer(this.receiverUserId, this.amount, this.transferMessage).subscribe({
      next: (res) => {
        this.transferSuccess.set(`₹${res.amount} sent successfully to ${res.receiverWallet.userFullName}.`);
        this.transferring.set(false);
        this.receiverUserId = 0;
        this.amount = 0;
        this.transferMessage = '';
        this.loadTransactions();
      },
      error: (err: HttpErrorResponse) => {
        this.transferError.set(err.error?.message || 'Transfer failed. Please try again.');
        this.transferring.set(false);
      }
    });
  }

  statusClass(status: string): string {
    switch (status) {
      case 'COMPLETED': return 'bg-green-50 text-green-700 border-green-200';
      case 'FAILED':    return 'bg-red-50 text-red-700 border-red-200';
      default:          return 'bg-yellow-50 text-yellow-700 border-yellow-200';
    }
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleString('en-IN', {
      day: '2-digit', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }
}
