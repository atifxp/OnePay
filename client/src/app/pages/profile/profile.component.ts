import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService, UserProfile } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  profile = signal<UserProfile | null>(null);
  loading = signal(true);
  saving = signal(false);
  loadError = signal('');
  saveError = signal('');
  saveSuccess = signal('');

  fullName = '';
  email = '';

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (user) => {
        this.profile.set(user);
        this.fullName = user.fullName;
        this.email = user.email;
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.loadError.set(err.error?.message || 'Failed to load profile.');
        this.loading.set(false);
      }
    });
  }

  onSave(): void {
    this.saveError.set('');
    this.saveSuccess.set('');
    this.saving.set(true);

    this.userService.updateProfile({ fullName: this.fullName, email: this.email }).subscribe({
      next: (updated) => {
        this.profile.set(updated);
        this.saveSuccess.set('Profile updated successfully.');
        this.saving.set(false);
        setTimeout(() => this.saveSuccess.set(''), 3000);
      },
      error: (err: HttpErrorResponse) => {
        this.saveError.set(err.error?.message || 'Failed to update profile.');
        this.saving.set(false);
      }
    });
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.router.navigate(['/login']),
    });
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      VERIFIED: 'bg-green-50 text-green-700',
      REJECTED: 'bg-red-50 text-red-700',
      PENDING: 'bg-yellow-50 text-yellow-700',
    };
    return map[status] ?? 'bg-gray-100 text-gray-600';
  }
}
