import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export type LoanType = 'PERSONAL' | 'HOME' | 'VEHICLE' | 'EDUCATION' | 'OTHER';
export type LoanStatus = 'SUBMITTED' | 'APPROVED' | 'REJECTED';

export interface LoanApplicationRequest {
  loanType: LoanType;
  loanAmount: number;
  tenureMonth: number;
  annualIncome: number;
  purpose?: string;
}

export interface LoanApplication {
  loanId: number;
  userId: number;
  loanType: LoanType;
  loanAmount: number;
  tenureMonth: number;
  interestRate: number;
  annualIncome: number;
  purpose: string;
  loanStatus: LoanStatus;
}

export interface LoanApprovalRequest {
  loanId: number;
  loanStatus: LoanStatus;
}

export interface LoanApprovalResponse {
  approvalId: number;
  loanId: number;
  officerId: number;
  loanStatus: LoanStatus;
}

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class LoanService {
  private api = `${environment.apiUrl}/loans`;

  constructor(private http: HttpClient) {}

  apply(data: LoanApplicationRequest) {
    return this.http.post<LoanApplication>(`${this.api}/apply`, data, OPTIONS);
  }

  getById(loanId: number) {
    return this.http.get<LoanApplication>(`${this.api}/${loanId}`, OPTIONS);
  }

  getAll() {
    return this.http.get<LoanApplication[]>(this.api, OPTIONS);
  }

  getMyLoans() {
    return this.http.get<LoanApplication[]>(`${this.api}/my-loans`, OPTIONS);
  }

  updateStatus(data: LoanApprovalRequest) {
    return this.http.post<LoanApprovalResponse>(`${this.api}/approval`, data, OPTIONS);
  }
}
