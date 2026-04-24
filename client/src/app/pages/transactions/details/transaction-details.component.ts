import { Component, signal, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { TransactionService, Transaction } from '../../../services/transaction.service';

@Component({
  selector: 'app-transaction-details',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './transaction-details.component.html'
})
export class TransactionDetailsComponent implements OnInit {
  transaction = signal<Transaction | null>(null);
  loading = signal(true);
  error = signal('');

  constructor(
    private transactionService: TransactionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('transactionId'));
    this.transactionService.getTransactionById(id).subscribe({
      next: (res) => {
        this.transaction.set(res);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Transaction not found.');
        this.loading.set(false);
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
