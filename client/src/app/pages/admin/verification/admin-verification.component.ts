import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { VerificationService, VerificationPending } from '../../../services/verification.service';

@Component({
  selector: 'app-admin-verification',
  standalone: true,
  imports: [],
  template: `
    <div class="min-h-screen bg-gray-50 px-4 py-8">
      <div class="max-w-4xl mx-auto">
        <div class="mb-6">
          <h1 class="text-2xl font-semibold text-gray-900">Pending Verifications</h1>
          <p class="text-sm text-gray-500 mt-1">
            Review and action user identity verification requests
          </p>
        </div>

        @if (error()) {
          <div
            class="bg-red-50 border border-red-200 text-red-600 text-sm rounded-lg px-4 py-3 mb-4"
          >
            {{ error() }}
          </div>
        }

        @if (successMsg()) {
          <div
            class="bg-green-50 border border-green-200 text-green-600 text-sm rounded-lg px-4 py-3 mb-4"
          >
            {{ successMsg() }}
          </div>
        }

        @if (loading()) {
          <div class="text-center py-12 text-gray-400 text-sm">Loading...</div>
        } @else if (items().length === 0) {
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm p-8 text-center">
            <p class="text-gray-500 text-sm">No pending verifications.</p>
          </div>
        } @else {
          <div class="space-y-3">
            @for (item of items(); track item.verificationId) {
              <div class="bg-white border border-gray-200 rounded-xl shadow-sm px-5 py-4">
                <div class="flex items-start justify-between gap-4">
                  <div class="flex-1 min-w-0">
                    <p class="text-sm font-medium text-gray-900">{{ item.user.fullName }}</p>
                    <p class="text-xs text-gray-500 mt-0.5">
                      {{ item.user.email }} · {{ item.user.phone }}
                    </p>
                    <p class="text-xs text-gray-400 mt-1">
                      {{ item.docType }} · {{ item.docNumber }}
                    </p>
                  </div>

                  <div class="flex items-center gap-2 shrink-0">
                    <span
                      class="text-xs font-medium px-2.5 py-1 rounded-full bg-yellow-50 text-yellow-700"
                    >
                      {{ item.verificationStatus }}
                    </span>
                    <button
                      (click)="approve(item.user.userId)"
                      [disabled]="acting() === item.user.userId"
                      class="bg-green-600 hover:bg-green-700 disabled:opacity-50 text-white rounded-lg px-3 py-1.5 text-xs font-medium transition-colors"
                    >
                      Approve
                    </button>
                    <button
                      (click)="reject(item.user.userId)"
                      [disabled]="acting() === item.user.userId"
                      class="bg-red-50 hover:bg-red-100 disabled:opacity-50 text-red-600 border border-red-200 rounded-lg px-3 py-1.5 text-xs font-medium transition-colors"
                    >
                      Reject
                    </button>
                  </div>
                </div>
              </div>
            }
          </div>

          <div class="flex items-center justify-between mt-6">
            <p class="text-xs text-gray-500">
              Page {{ page() + 1 }} of {{ totalPages() }} · {{ totalElements() }} total
            </p>
            <div class="flex gap-2">
              <button
                (click)="prevPage()"
                [disabled]="page() === 0"
                class="border border-gray-200 rounded-lg px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-50 disabled:opacity-40 transition-colors"
              >
                Previous
              </button>
              <button
                (click)="nextPage()"
                [disabled]="page() + 1 >= totalPages()"
                class="border border-gray-200 rounded-lg px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-50 disabled:opacity-40 transition-colors"
              >
                Next
              </button>
            </div>
          </div>
        }
      </div>
    </div>
  `,
})
export class AdminVerificationComponent implements OnInit {
  items = signal<VerificationPending[]>([]);
  loading = signal(true);
  acting = signal<number | null>(null);
  error = signal('');
  successMsg = signal('');
  page = signal(0);
  totalPages = signal(0);
  totalElements = signal(0);
  private readonly pageSize = 10;

  constructor(private verificationService: VerificationService) {}

  ngOnInit(): void {
    this.loadPage();
  }

  private loadPage(): void {
    this.loading.set(true);
    this.error.set('');
    this.verificationService.getPending(this.page(), this.pageSize).subscribe({
      next: (res) => {
        this.items.set(res.content);
        this.totalPages.set(res.page.totalPages);
        this.totalElements.set(res.page.totalElements);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to load verifications.');
        this.loading.set(false);
      },
    });
  }

  approve(userId: number): void {
    this.acting.set(userId);
    this.error.set('');
    this.successMsg.set('');
    this.verificationService.approve(userId).subscribe({
      next: (res) => {
        this.successMsg.set(res.message || 'User approved.');
        this.acting.set(null);
        this.items.update((list) => list.filter((i) => i.user.userId !== userId));
        this.totalElements.update((n) => n - 1);
        setTimeout(() => this.successMsg.set(''), 3000);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to approve.');
        this.acting.set(null);
      },
    });
  }

  reject(userId: number): void {
    this.acting.set(userId);
    this.error.set('');
    this.successMsg.set('');
    this.verificationService.reject(userId).subscribe({
      next: (res) => {
        this.successMsg.set(res.message || 'User rejected.');
        this.acting.set(null);
        this.items.update((list) => list.filter((i) => i.user.userId !== userId));
        this.totalElements.update((n) => n - 1);
        setTimeout(() => this.successMsg.set(''), 3000);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Failed to reject.');
        this.acting.set(null);
      },
    });
  }

  prevPage(): void {
    this.page.update((p) => p - 1);
    this.loadPage();
  }

  nextPage(): void {
    this.page.update((p) => p + 1);
    this.loadPage();
  }
}
