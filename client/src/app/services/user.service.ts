import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

export interface UserProfile {
  userId: number;
  fullName: string;
  email: string;
  phone: string;
  role: string;
  accountStatus: string;
}

export interface UserUpdateRequest {
  fullName: string;
  email: string;
}

const OPTIONS = { withCredentials: true };

@Injectable({ providedIn: 'root' })
export class UserService {
  private api = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  getProfile() {
    return this.http.get<UserProfile>(`${this.api}/me`, OPTIONS);
  }

  updateProfile(data: UserUpdateRequest) {
    return this.http.patch<UserProfile>(`${this.api}/me`, data, OPTIONS);
  }
}
