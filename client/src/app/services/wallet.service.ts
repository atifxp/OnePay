import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface WalletResponse {
  walletId: number;
  user: {
    userId: number;
    fullName: string;
    email: string;
    phone: string;
    role: string;
    accountStatus: string;
  };
  balance: number;
  currency: string;
}

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class WalletService {
  private api = `${environment.apiUrl}/wallets`;

  constructor(private http: HttpClient) {}

  getMyWallet() {
    return this.http.get<WalletResponse>(`${this.api}/me`, OPTIONS);
  }

  createWallet(userId: number) {
    return this.http.post<WalletResponse>(`${this.api}/create`, { userId }, OPTIONS);
  }
}
