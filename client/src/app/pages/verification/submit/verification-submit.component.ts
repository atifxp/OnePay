import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { VerificationService } from '../../../services/verification.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-verification-submit',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './verification-submit.component.html'
})
export class VerificationSubmitComponent {
  docType = 'AADHAR';
  docNumber = '';
  loading = signal(false);
  error = signal('');
  success = signal('');

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

  onSubmit(): void {
    this.error.set('');
    this.success.set('');
    this.loading.set(true);

    this.verificationService.submit(this.docType, this.docNumber).subscribe({
      next: (res) => {
        this.success.set(res.message);
        this.loading.set(false);
        this.docNumber = '';
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message || 'Submission failed. Please try again.');
        this.loading.set(false);
      }
    });
  }
}
