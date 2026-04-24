import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { UserService } from '../services/user.service';

export const adminGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const router = inject(Router);

  return userService.getProfile().pipe(
    map((user) => {
      if (user.role === 'CUSTOMER') {
        return router.createUrlTree(['/dashboard']);
      }
      return true;
    }),
    catchError(() => of(router.createUrlTree(['/login']))),
  );
};
