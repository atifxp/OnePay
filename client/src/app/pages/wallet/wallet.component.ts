import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { WalletService, WalletResponse } from '../../services/wallet.service';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './wallet.component.html'
})
export class WalletComponent implements OnInit {
  wallet = signal<WalletResponse | null>(null);
  loading = signal(true);
  error = signal('');
  notFound = signal(false);

  constructor(private walletService: WalletService) {}

  ngOnInit(): void {
    this.walletService.getMyWallet().subscribe({
      next: (res) => {
        this.wallet.set(res);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404 || err.status === 500) {
          this.notFound.set(true);
        } else {
          this.error.set(err.error?.message || 'Failed to load wallet.');
        }
        this.loading.set(false);
      }
    });
  }

  formatBalance(balance: number): string {
    return new Intl.NumberFormat('en-IN', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(balance);
  }
}
