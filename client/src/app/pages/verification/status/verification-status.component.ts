import { Component, signal, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { VerificationService } from '../../../services/verification.service';

@Component({
  selector: 'app-verification-status',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './verification-status.component.html'
})
export class VerificationStatusComponent implements OnInit {
  status = signal<string | null>(null);
  loading = signal(true);
  error = signal('');

  constructor(private verificationService: VerificationService) {}

  ngOnInit(): void {
    this.verificationService.getStatus().subscribe({
      next: (res) => {
        this.status.set(res.status);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Could not fetch status.');
        this.loading.set(false);
      }
    });
  }
}
