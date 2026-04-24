import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-8 w-full max-w-sm">

        <h1 class="text-2xl font-semibold text-gray-900">Create account</h1>
        <p class="text-sm text-gray-500 mt-1 mb-6">Join OnePay today</p>

        @if (error()) {
          <div class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ error() }}
          </div>
        }

        @if (success()) {
          <div class="bg-green-50 border border-green-200 text-green-600 text-sm rounded-lg px-4 py-3 mb-4">
            {{ success() }}
          </div>
        }

        <form (ngSubmit)="onRegister()" #registerForm="ngForm" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
            <input
              type="text"
              name="fullName"
              [(ngModel)]="fullName"
              required
              placeholder="Enter your full name"
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
              placeholder="Enter your email"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Phone Number</label>
            <input
              type="text"
              name="phone"
              [(ngModel)]="phone"
              required
              minlength="10"
              placeholder="Enter your phone number"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Password</label>
            <input
              type="password"
              name="passwordHash"
              [(ngModel)]="passwordHash"
              required
              placeholder="Create a password"
              class="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
            />
          </div>

          <button
            type="submit"
            [disabled]="loading() || registerForm.invalid"
            class="w-full bg-indigo-600 hover:bg-indigo-700 disabled:opacity-50 text-white rounded-lg py-2 text-sm font-medium transition-colors"
          >
            {{ loading() ? 'Creating account...' : 'Register' }}
          </button>
        </form>

        <p class="text-sm text-gray-500 text-center mt-6">
          Already have an account?
          <a routerLink="/login" class="text-indigo-600 hover:underline font-medium">Login</a>
        </p>

      </div>
    </div>
  `
})
export class RegisterComponent {
  fullName = '';
  email = '';
  phone = '';
  passwordHash = '';
  loading = signal(false);
  error = signal('');
  success = signal('');

  constructor(private authService: AuthService, private router: Router) {}

  onRegister() {
    this.error.set('');
    this.success.set('');
    this.loading.set(true);

    this.authService.register(this.fullName, this.email, this.phone, this.passwordHash).subscribe({
      next: (res) => {
        this.success.set(res.message);
        this.loading.set(false);
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err: any) => {
        this.error.set(err?.error?.message || 'Registration failed. Please try again.');
        this.loading.set(false);
      }
    });
  }
}
