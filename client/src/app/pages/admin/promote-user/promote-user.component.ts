import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { UserService, UserProfile } from '../../../services/user.service';

@Component({
  selector: 'app-promote-user',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './promote-user.component.html',
})
export class PromoteUserComponent {
  searchId: number | null = null;
  selectedRole = 'LOAN_OFFICER';

  foundUser = signal<UserProfile | null>(null);
  fetching = signal(false);
  promoting = signal(false);
  fetchError = signal('');
  promoteError = signal('');
  promoteSuccess = signal('');

  constructor(private userService: UserService) {}

  fetchUser(): void {
    if (!this.searchId) return;
    this.fetching.set(true);
    this.fetchError.set('');
    this.foundUser.set(null);
    this.promoteSuccess.set('');
    this.promoteError.set('');

    this.userService.getById(this.searchId).subscribe({
      next: (user) => {
        this.foundUser.set(user);
        this.selectedRole = user.role;
        this.fetching.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.fetchError.set(err.error?.message || 'User not found.');
        this.fetching.set(false);
      },
    });
  }

  promote(): void {
    const user = this.foundUser();
    if (!user) return;
    this.promoting.set(true);
    this.promoteError.set('');
    this.promoteSuccess.set('');

    this.userService.promoteUser({ userId: user.userId, role: this.selectedRole }).subscribe({
      next: (res) => {
        this.promoteSuccess.set(res.message || `User promoted to ${this.selectedRole}.`);
        this.foundUser.update((u) => (u ? { ...u, role: this.selectedRole } : u));
        this.promoting.set(false);
        setTimeout(() => this.promoteSuccess.set(''), 4000);
      },
      error: (err: HttpErrorResponse) => {
        this.promoteError.set(err.error?.message || 'Failed to promote user.');
        this.promoting.set(false);
      },
    });
  }
}
