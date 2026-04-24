import { Component, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserService, UserProfile } from '../../services/user.service';
import { WalletService, WalletResponse } from '../../services/wallet.service';
import { TransactionService, Transaction } from '../../services/transaction.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  user = signal<UserProfile | null>(null);
  wallet = signal<WalletResponse | null>(null);
  recentTransactions = signal<Transaction[]>([]);
  loading = signal(true);
  walletError = signal<string | null>(null);

  constructor(
    private userService: UserService,
    private walletService: WalletService,
    private transactionService: TransactionService,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userService.getProfile().subscribe({
      next: (u) => this.user.set(u),
      error: () => {},
    });

    this.walletService.getMyWallet().subscribe({
      next: (w) => this.wallet.set(w),
      error: () => this.walletError.set('No wallet found'),
    });

    this.transactionService.getMyTransactions(0, 5).subscribe({
      next: (page) => this.recentTransactions.set(page.content),
      error: () => {},
      complete: () => this.loading.set(false),
    });
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amount);
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleDateString('en-IN', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });
  }

  statusColor(status: string): string {
    return status === 'COMPLETED'
      ? 'text-green-600'
      : status === 'FAILED'
        ? 'text-red-500'
        : 'text-yellow-500';
  }

  isCredit(tx: Transaction): boolean {
    return this.wallet()?.walletId === tx.receiverWallet.walletId;
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.router.navigate(['/login']),
    });
  }
}
