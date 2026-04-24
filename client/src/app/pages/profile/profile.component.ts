import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService, UserProfile } from '../../services/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="min-h-screen bg-gray-50 px-4 py-8">
      <div class="max-w-lg mx-auto">

        <div class="mb-6">
          <h1 class="text-2xl font-semibold text-gray-900">Profile</h1>
          <p class="text-sm text-gray-500 mt-1">View and update your account details</p>
        </div>

        @if (loadError()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3">
            {{ loadError() }}
          </div>
        }

        @if (loading()) {
          <div class="text-center py-12 text-gray-400 text-sm">Loading...</div>
        } @else if (profile()) {

          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-6 mb-4">
            <div class="flex items-start justify-between mb-4">
              <div>
                <p class="text-base font-semibold text-gray-900">{{ profile()!.fullName }}</p>
                <p class="text-xs text-gray-400 mt-0.5">User #{{ profile()!.userId }}</p>
              </div>
              <div class="flex gap-2">
                <span class="text-xs font-medium px-2.5 py-1 rounded-full bg-indigo-50 text-indigo-700">
                  {{ profile()!.role }}
                </span>
                <span class="text-xs font-medium px-2.5 py-1 rounded-full {{ statusClass(profile()!.accountStatus) }}">
                  {{ profile()!.accountStatus }}
                </span>
              </div>
            </div>

            <div class="space-y-2 text-sm">
              <div class="flex justify-between py-2 border-t border-gray-100">
                <span class="text-gray-500">Email</span>
                <span class="text-gray-900 font-medium">{{ profile()!.email }}</span>
              </div>
              <div class="flex justify-between py-2 border-t border-gray-100">
                <span class="text-gray-500">Phone</span>
                <span class="text-gray-900 font-medium">{{ profile()!.phone }}</span>
              </div>
            </div>
          </div>

          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-6">
            <h2 class="text-sm font-semibold text-gray-900 mb-4">Edit Details</h2>

            @if (saveError()) {
              <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4">
                {{ saveError() }}
              </div>
            }

            @if (saveSuccess()) {
              <div class="bg-green-50 border border-green-200 text-green-600 text-sm rounded-lg px-4 py-3 mb-4">
                {{ saveSuccess() }}
              </div>
            }

            <form (ngSubmit)="onSave()" #profileForm="ngForm" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
                <input
                  type="text"
                  name="fullName"
                  [(ngModel)]="fullName"
                  required
                  placeholder="Your full name"
                  class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input
                  type="email"
                  name="email"
                  [(ngModel)]="email"
                  required
                  email
                  placeholder="Your email address"
                  class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>

              <button
                type="submit"
                [disabled]="saving() || profileForm.invalid"
                class="w-full bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50 text-white rounded-lg py-2 text-sm font-medium transition-colors"
              >
                {{ saving() ? 'Saving...' : 'Save Changes' }}
              </button>
            </form>
          </div>

        }

      </div>
    </div>
  `
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

  constructor(private userService: UserService) {}

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

  statusClass(status: string): string {
    const map: Record<string, string> = {
      VERIFIED: 'bg-green-50 text-green-700',
      REJECTED: 'bg-red-50 text-red-700',
      PENDING: 'bg-yellow-50 text-yellow-700',
    };
    return map[status] ?? 'bg-gray-100 text-gray-600';
  }
}
