import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(phone: string, password: string) {
    return this.http.post<{ user: any; message: string }>(`${this.api}/login`, { phone, password }, OPTIONS);
  }

  register(fullName: string, email: string, phone: string, passwordHash: string) {
    return this.http.post<{ message: string }>(`${this.api}/register`, { fullName, email, phone, passwordHash }, OPTIONS);
  }

  logout() {
    return this.http.post<{ message: string }>(`${this.api}/logout`, {}, OPTIONS);
  }
}
