import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export type TransactionStatus = 'PENDING' | 'COMPLETED' | 'FAILED';

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
  transactionStatus: TransactionStatus;
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

export interface TransferRequest {
  receiverUserId: number;
  amount: number;
  message?: string;
}

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private api = `${environment.apiUrl}/transaction`;

  constructor(private http: HttpClient) {}

  transfer(data: TransferRequest) {
    return this.http.post<Transaction>(`${this.api}/transfer`, data, OPTIONS);
  }

  getMyTransactions(pageNo: number, pageSize: number) {
    return this.http.get<SpringPage<Transaction>>(`${this.api}/?page=${pageNo}&size=${pageSize}`, OPTIONS);
  }

  getById(transactionId: number) {
    return this.http.get<Transaction>(`${this.api}/${transactionId}`, OPTIONS);
  }

  getAll(pageNo: number, pageSize: number) {
    return this.http.get<SpringPage<Transaction>>(`${this.api}/all?page=${pageNo}&size=${pageSize}`, OPTIONS);
  }
}
