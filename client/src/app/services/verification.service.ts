import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface VerificationPending {
  verificationId: number;
  user: {
    userId: number;
    fullName: string;
    email: string;
    phone: string;
    role: string;
    accountStatus: string;
  };
  docType: string;
  docNumber: string;
  verificationStatus: string;
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
export class VerificationService {
  private api = `${environment.apiUrl}/verification`;

  constructor(private http: HttpClient) {}

  submit(docType: string, docNumber: string) {
    return this.http.post<{ message: string; status: string }>(
      `${this.api}/submit`,
      { docType, docNumber },
      OPTIONS
    );
  }

  getStatus() {
    return this.http.get<{ status: string }>(`${this.api}/status`, OPTIONS);
  }

  getPending(pageNo: number, pageSize: number) {
    return this.http.get<SpringPage<VerificationPending>>(
      `${this.api}/pending/${pageNo}/${pageSize}`,
      OPTIONS
    );
  }

  approve(userId: number) {
    return this.http.patch<{ message: string }>(
      `${this.api}/${userId}/approve`,
      { verificationStatus: 'VERIFIED' },
      OPTIONS
    );
  }

  reject(userId: number) {
    return this.http.patch<{ message: string }>(
      `${this.api}/${userId}/reject`,
      { verificationStatus: 'REJECTED' },
      OPTIONS
    );
  }
}
