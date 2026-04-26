import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { VerificationService, VerificationPending } from '../../../services/verification.service';

@Component({
  selector: 'app-admin-verification',
  standalone: true,
  imports: [],
  templateUrl: './admin-verification.component.html',
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
