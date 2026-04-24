import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface WalletSummary {
  walletId: number;
  userId: number;
  userFullName: string;
}

export interface Transaction {
  transactionId: number;
  senderWallet: WalletSummary;
  receiverWallet: WalletSummary;
  amount: number;
  transactionStatus: 'PENDING' | 'COMPLETED' | 'FAILED';
  message: string;
  initiatedAt: string;
  completedAt: string | null;
}

export interface SpringPage<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private api = `${environment.apiUrl}/transaction`;

  constructor(private http: HttpClient) {}

  transfer(receiverUserId: number, amount: number, message: string) {
    return this.http.post<Transaction>(`${this.api}/transfer`, { receiverUserId, amount, message }, OPTIONS);
  }

  getMyTransactions(page = 0, size = 10) {
    return this.http.get<SpringPage<Transaction>>(`${this.api}/`, { ...OPTIONS, params: { page, size } });
  }

  getTransactionById(transactionId: number) {
    return this.http.get<Transaction>(`${this.api}/${transactionId}`, OPTIONS);
  }

  getAllTransactions(page = 0, size = 10) {
    return this.http.get<SpringPage<Transaction>>(`${this.api}/all`, { ...OPTIONS, params: { page, size } });
  }

  getAll(page = 0, size = 10) {
    return this.getAllTransactions(page, size);
  }
}
