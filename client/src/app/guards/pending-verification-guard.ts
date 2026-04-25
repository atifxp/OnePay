import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of, switchMap } from 'rxjs';
import { UserService } from '../services/user.service';
import { VerificationService } from '../services/verification.service';

export const pendingVerificationGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const verificationService = inject(VerificationService);
  const router = inject(Router);

  return userService.getProfile().pipe(
    switchMap((user) => {
      if (user.role !== 'CUSTOMER') {
        return of(true);
      }

      return verificationService.getStatus().pipe(
        map((response: { status: string | null }) => {
          if (response?.status === 'VERIFIED') {
            return true;
          } else {
            return router.createUrlTree(['/verification/status']);
          }
        }),
      );
    }),
    catchError(() => of(router.createUrlTree(['/verification/submit']))),
  );
};
