import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { VerificationService } from '../services/verification.service';
import { catchError, map, of, switchMap } from 'rxjs';

export const approvedVerificationGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const verificationService = inject(VerificationService);

  const router = inject(Router);
  return userService.getProfile().pipe(
    switchMap((user) => {
      if (user.role !== 'CUSTOMER') {
        return of(router.createUrlTree(['/dashboard']));
      }

      return verificationService.getStatus().pipe(
        map((response: { status: string | null }) => {
          if (response?.status === 'VERIFIED') {
            return router.createUrlTree(['/verification/status']);
          } else {
            return true;
          }
        }),
      );
    }),
    catchError(() => of(router.createUrlTree(['/verification/submit']))),
  );
};
