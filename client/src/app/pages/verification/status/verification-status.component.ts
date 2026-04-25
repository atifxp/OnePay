import { Component, signal, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { VerificationService } from '../../../services/verification.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-verification-status',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './verification-status.component.html',
})
export class VerificationStatusComponent implements OnInit {
  status = signal<string | null>(null);
  loading = signal(true);
  error = signal('');

  constructor(
    private verificationService: VerificationService,
    private authService: AuthService,
    private router: Router,
  ) {}

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.router.navigate(['/login']),
    });
  }

  ngOnInit(): void {
    this.verificationService.getStatus().subscribe({
      next: (res) => {
        this.status.set(res.status);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Could not fetch status.');
        this.loading.set(false);
      },
    });
  }
}
